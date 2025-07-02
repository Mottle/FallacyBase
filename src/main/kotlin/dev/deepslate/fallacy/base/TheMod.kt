package dev.deepslate.fallacy.base

import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.common.Mod
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Mod(TheMod.ID)
object TheMod {
    fun withID(name: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(ID, name)

    const val ID = "fallacy_base"

    val LOGGER: Logger = LoggerFactory.getLogger(ID)
}
