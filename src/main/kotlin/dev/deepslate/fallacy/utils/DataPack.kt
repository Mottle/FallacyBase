package dev.deepslate.fallacy.utils

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.LevelAccessor
import kotlin.jvm.optionals.getOrNull

fun <T> LevelAccessor.datapack(registry: ResourceKey<Registry<T>>, key: ResourceKey<T>) =
    registryAccess().lookup(registry).getOrNull()?.get(key)?.getOrNull()?.value()