package dev.deepslate.fallacy.base.trait

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer

interface TickTrait : Trait {
    val interval: Int

    fun tick(level: ServerLevel, player: ServerPlayer, position: BlockPos)
}