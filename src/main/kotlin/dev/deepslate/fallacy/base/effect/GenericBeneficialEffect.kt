package dev.deepslate.fallacy.base.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory

open class GenericBeneficialEffect(color: Int): MobEffect(MobEffectCategory.BENEFICIAL, color) {
    companion object {
        fun <T> of(color: Int) = { _: T -> GenericBeneficialEffect(color) }
    }
}