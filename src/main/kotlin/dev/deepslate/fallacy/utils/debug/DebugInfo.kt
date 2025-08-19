package dev.deepslate.fallacy.utils.debug

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

data class DebugInfo(val key: ResourceLocation, val subscribers: List<ServerPlayer>) {
    fun subscribe(player: ServerPlayer) = copy(subscribers = subscribers + player)
}