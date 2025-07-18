package dev.deepslate.fallacy.utils

import dev.deepslate.fallacy.base.TheMod
import net.minecraft.ReportType
import net.minecraft.ReportedException
import net.minecraft.Util
import net.minecraft.server.Bootstrap
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent
import net.neoforged.neoforge.event.server.ServerStoppingEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CompletionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

object Worker {

    @JvmStatic
    val LOGGER: Logger = LoggerFactory.getLogger("fallacy-worker")

    private var ioPool = makeExecutor("IO", false)

    @JvmStatic
    val IO_POOL: ExecutorService
        get() = ioPool

    private fun makeExecutor(name: String, daemon: Boolean): ExecutorService {
        val atom = AtomicInteger(0)

        return Executors.newCachedThreadPool { runnable ->
            val thread = Thread(runnable)

            thread.name = "name-${atom.andIncrement}"
            thread.isDaemon = daemon
            thread.uncaughtExceptionHandler = exceptionHandler

            return@newCachedThreadPool thread
        }
    }

    private val exceptionHandler = Thread.UncaughtExceptionHandler { thread, throwable ->
        Util.pauseInIde(throwable)

        if (throwable is CompletionException) {
            LOGGER.error(String.format(Locale.ROOT, "Caught exception in thread %s", thread), throwable.cause)
            return@UncaughtExceptionHandler
        }

        if (throwable is ReportedException) {
            Bootstrap.realStdoutPrintln(throwable.report.getFriendlyReport(ReportType.CRASH))
            exitProcess(-1)
        }

        LOGGER.error(String.format(Locale.ROOT, "Caught exception in thread %s", thread), throwable)
    }

    @EventBusSubscriber(modid = TheMod.ID)
    object Handler {
        @SubscribeEvent(priority = EventPriority.LOWEST)
        fun onServerClosing(event: ServerStoppingEvent) {
            LOGGER.info("Shutting down IO thread pool.")
            IO_POOL.shutdown()

            val stopped = try {
                IO_POOL.awaitTermination(3, TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                LOGGER.error("Occur Exception on IO Thread shutdown: ", e)
                false
            }

            if (!stopped) {
                LOGGER.warn("IO thread pool did not terminate in time. Shutting down forcefully")
                IO_POOL.shutdownNow()
            }

            LOGGER.info("IO thread pool shutdown complete")
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onServerAboutToStart(event: ServerAboutToStartEvent) {
            if (ioPool.isShutdown) {
                ioPool = makeExecutor("IO", false)
            }
        }
    }
}