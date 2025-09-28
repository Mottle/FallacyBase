package dev.deepslate.fallacy.utils

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player

fun checkInvulnerable(entity: Entity): Boolean {
    if (entity is Player) {
        if (entity.abilities.invulnerable) return true
    }

    return entity.isInvulnerable
}