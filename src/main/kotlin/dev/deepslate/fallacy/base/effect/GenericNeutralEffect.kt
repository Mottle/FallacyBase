package dev.deepslate.fallacy.base.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory

class GenericNeutralEffect(color: Int) : MobEffect(MobEffectCategory.NEUTRAL, color) {
    companion object {
        fun <T> of(color: Int) = { _: T -> GenericNeutralEffect(color) }
    }
}