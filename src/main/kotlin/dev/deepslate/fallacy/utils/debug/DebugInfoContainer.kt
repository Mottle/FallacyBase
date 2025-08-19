package dev.deepslate.fallacy.utils.debug

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DebugInfoContainer {

    val logger: Logger = LoggerFactory.getLogger(DebugInfoContainer::class.java)

    val debugInfos: MutableMap<ResourceLocation, DebugInfo> = mutableMapOf()
    val debugContents: MutableMap<ResourceLocation, DebugContent> = mutableMapOf()

    fun register(id: ResourceLocation) {
        if (debugInfos.containsKey(id)) {
            logger.warn("Debug info with id $id already registered!")
            return
        }
        debugInfos[id] = DebugInfo(id, emptyList())
    }
}