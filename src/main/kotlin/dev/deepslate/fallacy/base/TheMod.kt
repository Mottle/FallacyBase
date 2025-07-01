package dev.deepslate.fallacy.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import net.neoforged.fml.common.Mod

@Mod(TheMod.ID)
object TheMod {
    const val ID = "fallacy_base"

    val LOGGER: Logger = LoggerFactory.getLogger(ID)
}
