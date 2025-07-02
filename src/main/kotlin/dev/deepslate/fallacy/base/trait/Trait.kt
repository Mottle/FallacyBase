package dev.deepslate.fallacy.base.trait

import dev.deepslate.fallacy.base.ModAttachments
import dev.deepslate.fallacy.base.TheMod
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.Entity
import net.neoforged.neoforge.registries.RegistryBuilder

interface Trait {

    companion object {
        @JvmStatic
        val KEY: ResourceKey<Registry<Trait>> = ResourceKey.createRegistryKey<Trait>(TheMod.withID("behavior"))

        @JvmStatic
        val REGISTRY: Registry<Trait> = RegistryBuilder(KEY).sync(false).create()

        fun has(entity: Entity, trait: Trait): Boolean {
            if (!entity.hasData(ModAttachments.TRAITS)) return false
            return entity.getData(ModAttachments.TRAITS).contains(trait)
        }

        fun has(entity: Entity, trait: Holder<Trait>): Boolean = has(entity, trait.value())

        fun add(entity: Entity, trait: Trait) {

            if (!REGISTRY.contains(trait)) throw IllegalArgumentException("Trait ${trait.javaClass} is not registered")

            val container = entity.getData(ModAttachments.TRAITS)
            container.add(trait).let { new ->
                entity.setData(ModAttachments.TRAITS, new)
            }
        }

        fun add(entity: Entity, trait: Holder<Trait>) {
            add(entity, trait.value())
        }

        private fun addAllRaw(entity: Entity, traits: Collection<Trait>) {
            val container = entity.getData(ModAttachments.TRAITS)
            container.addAll(traits).let { new ->
                entity.setData(ModAttachments.TRAITS, new)
            }
        }

        fun addAll(entity: Entity, behaviors: Collection<Holder<Trait>>) {
            addAllRaw(entity, behaviors.map { it.value() })
        }

        fun get(entity: Entity): TraitContainer {
            if (entity.hasData(ModAttachments.TRAITS)) {
                return entity.getData(ModAttachments.TRAITS)
            }
            return TraitContainer.empty()
        }

        fun remove(entity: Entity, trait: Trait) {
            if (!entity.hasData(ModAttachments.TRAITS)) return
            val container = entity.getData(ModAttachments.TRAITS)
            container.remove(trait).let { new ->
                entity.setData(ModAttachments.TRAITS, new)
            }
        }

        fun remove(entity: Entity, trait: Holder<Trait>) {
            remove(entity, trait.value())
        }

        private fun removeAllRaw(entity: Entity, traits: Collection<Trait>) {
            if (!entity.hasData(ModAttachments.TRAITS)) return
            val container = entity.getData(ModAttachments.TRAITS)
            container.removeAll(traits).let { new ->
                entity.setData(ModAttachments.TRAITS, new)
            }
        }

        fun removeAll(entity: Entity, behaviors: Collection<Holder<Trait>>) {
            removeAllRaw(entity, behaviors.map { it.value() })
        }
    }
}