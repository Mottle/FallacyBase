package dev.deepslate.fallacy.base.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory

class GenericHarmfulEffect(color: Int) : MobEffect(MobEffectCategory.HARMFUL, color) {
    companion object {
        fun <T> of(color: Int) = { _: T -> GenericHarmfulEffect(color) }
    }
}