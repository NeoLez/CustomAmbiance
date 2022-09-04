package net.fabricmc.custom_ambiance.soundevent.predicates;

import net.fabricmc.custom_ambiance.ConfigSection;
import net.fabricmc.custom_ambiance.soundevent.CASoundEventData;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("unused")
public record IsBiome(RegistryKey<Biome> biome) implements SoundEventPredicate{
    @Override
    public boolean test(CASoundEventData caSoundEventData) {
        return caSoundEventData.world().getBiome(caSoundEventData.position()).matchesKey(biome);
    }

    public static SoundEventPredicate fromConfig(ConfigSection config){
        return new IsBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier(config.getString("biomeName"))));
    }
}
