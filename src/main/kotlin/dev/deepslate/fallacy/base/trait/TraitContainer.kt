package dev.deepslate.fallacy.base.trait

import com.mojang.serialization.Codec
import net.minecraft.resources.ResourceLocation

data class TraitContainer(private val traitSet: Set<Trait>) : Iterable<Trait> {
    companion object {
        fun empty() = TraitContainer(setOf())

        fun from(traits: Collection<Trait>) = TraitContainer(traits.toSet())

        private fun fromResourceLocations(keys: Collection<ResourceLocation>) =
            from(keys.mapNotNull(::fromResourceLocation))

        private fun fromResourceLocation(key: ResourceLocation): Trait? = Trait.REGISTRY.get(key)

        val CODEC: Codec<TraitContainer> =
            ResourceLocation.CODEC.listOf().xmap(::fromResourceLocations, TraitContainer::toResourceLocations)
    }

    fun toResourceLocations(): List<ResourceLocation> = traitSet.mapNotNull(Trait.REGISTRY::getKey)

    fun has(tag: ResourceLocation): Boolean = Trait.REGISTRY.get(tag)?.let { it in traitSet } == true

    fun has(trait: Trait): Boolean = trait in traitSet

    fun add(trait: Trait): TraitContainer = TraitContainer(traitSet + trait)

    fun remove(trait: Trait): TraitContainer = TraitContainer(traitSet - trait)

    fun addAll(traits: Collection<Trait>): TraitContainer = TraitContainer(traitSet + traits)

    fun removeAll(traits: Collection<Trait>): TraitContainer = TraitContainer(traitSet - traits)

    override fun iterator(): Iterator<Trait> = traitSet.iterator()
}