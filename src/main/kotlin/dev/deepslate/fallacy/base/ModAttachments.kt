package dev.deepslate.fallacy.base

import dev.deepslate.fallacy.base.trait.TraitContainer
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object ModAttachments {
    @JvmStatic
    private val registry = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TheMod.ID)

    @JvmStatic
    val TRAITS: DeferredHolder<AttachmentType<*>, AttachmentType<TraitContainer>> =
        registry.register("behaviors") { _ ->
            AttachmentType.builder(TraitContainer::empty).serialize(TraitContainer.CODEC).copyOnDeath().build()
        }

    @EventBusSubscriber(modid = TheMod.ID)
    object RegisterHandler {
        @SubscribeEvent
        fun onModConstructed(event: FMLConstructModEvent) {
            event.enqueueWork {
                registry.register(MOD_BUS)
            }
        }
    }
}
