package dev.deepslate.fallacy.base

import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.server.ServerLifecycleHooks

object TickCollector {
    @JvmStatic
    val serverTickCount: Int get() = ServerLifecycleHooks.getCurrentServer()?.tickCount ?: Int.MIN_VALUE

    @JvmStatic
    val clientTickCount: Int
        get() = if (internalClickTickCount > 0) internalClickTickCount else Int.MIN_VALUE

    @JvmStatic
    private var internalClickTickCount = 0

    //    @OnlyIn(Dist.CLIENT)
    @EventBusSubscriber(modid = TheMod.ID)
    object ClientHandler {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onClientTick(event: ClientTickEvent.Pre) {
            internalClickTickCount++
        }
    }

    fun checkServerTickInterval(interval: Int): Boolean {
        return serverTickCount % interval == 0
    }
}