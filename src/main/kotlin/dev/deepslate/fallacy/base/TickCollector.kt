package dev.deepslate.fallacy.base

import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import net.neoforged.bus.api.EventPriority
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.server.ServerLifecycleHooks

object TickCollector {
    @JvmStatic
    val serverTickCount: Int get() = ServerLifecycleHooks.getCurrentServer()?.tickCount ?: Int.MIN_VALUE

    @JvmStatic
    var clientTickCount: Int = Int.MIN_VALUE
        private set

    @OnlyIn(Dist.CLIENT)
    @EventBusSubscriber(modid = TheMod.ID)
    object ClientHandler {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        fun onClientTick(event: ClientTickEvent.Pre) {
            clientTickCount++
        }
    }

    fun checkServerTickInterval(interval: Int): Boolean {
        return serverTickCount % interval == 0
    }
}