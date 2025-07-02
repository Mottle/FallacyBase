package dev.deepslate.fallacy.base

import dev.deepslate.fallacy.base.trait.TraitContainer
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries

object ModAttachments {
    @JvmStatic
    private val registry = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TheMod.ID)

    @JvmStatic
    val TRAITS: DeferredHolder<AttachmentType<*>, AttachmentType<TraitContainer>> =
        registry.register("behaviors") { _ ->
            AttachmentType.builder(TraitContainer::empty).serialize(TraitContainer.CODEC).copyOnDeath().build()
        }
}