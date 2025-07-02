package dev.deepslate.fallacy.base.trait

import dev.deepslate.fallacy.base.TheMod
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.registries.NewRegistryEvent

@EventBusSubscriber(modid = TheMod.ID)
object RegisterHandler {
    @SubscribeEvent
    fun registerRegistries(event: NewRegistryEvent) {
        event.register(Trait.REGISTRY)
        TheMod.LOGGER.debug("Registered trait registry")
    }
}