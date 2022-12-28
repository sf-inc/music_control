package com.github.charlyb01.music_control.event;

import com.github.charlyb01.music_control.access.BiomeAccess;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import static net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys.biomeRegistryWrapper;

public class SoundEventBiome {
    public static void init() {
        final RegistryEntryLookup<Biome> biomeRegistryEntryLookup = biomeRegistryWrapper();

        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_PLAINS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_PLAINS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_PLAINS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.ICE_SPIKES).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.ICE_SPIKES).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_ICE_SPIKES);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_TAIGA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_TAIGA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_TAIGA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_BEACH).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SNOWY_BEACH).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_BEACH);
        }

        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_HILLS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_HILLS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_HILLS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.TAIGA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.TAIGA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_TAIGA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_PINE_TAIGA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_PINE_TAIGA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.STONY_SHORE).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.STONY_SHORE).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_STONY_SHORE);
        }

        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.PLAINS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.PLAINS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_PLAINS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SUNFLOWER_PLAINS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SUNFLOWER_PLAINS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SUNFLOWER_PLAINS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.FLOWER_FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.FLOWER_FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_FLOWER_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.BIRCH_FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.BIRCH_FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_BIRCH_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_BIRCH_FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.OLD_GROWTH_BIRCH_FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.DARK_FOREST).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.DARK_FOREST).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_DARK_FOREST);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.MANGROVE_SWAMP).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.MANGROVE_SWAMP).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_MANGROVE_SWAMP);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.JUNGLE).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.JUNGLE).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_JUNGLE);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SPARSE_JUNGLE).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SPARSE_JUNGLE).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SPARSE_JUNGLE);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.BAMBOO_JUNGLE).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.BAMBOO_JUNGLE).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_BAMBOO_JUNGLE);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.BEACH).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.BEACH).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_BEACH);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.MUSHROOM_FIELDS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.MUSHROOM_FIELDS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_MUSHROOM_FIELDS);
        }

        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.DESERT).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.DESERT).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_DESERT);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SAVANNA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SAVANNA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SAVANNA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.SAVANNA_PLATEAU).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.SAVANNA_PLATEAU).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_SAVANNA_PLATEAU);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_SAVANNA).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WINDSWEPT_SAVANNA).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_SAVANNA);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.BADLANDS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.BADLANDS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_BADLANDS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WOODED_BADLANDS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WOODED_BADLANDS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WOODED_BADLANDS);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.ERODED_BADLANDS).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.ERODED_BADLANDS).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_ERODED_BADLANDS);
        }

        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.RIVER).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.RIVER).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_RIVER);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.FROZEN_RIVER).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.FROZEN_RIVER).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_FROZEN_RIVER);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.WARM_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.WARM_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_WARM_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.LUKEWARM_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.LUKEWARM_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_LUKEWARM_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.COLD_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.COLD_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_COLD_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_COLD_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_COLD_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_COLD_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.FROZEN_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.FROZEN_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_FROZEN_OCEAN);
        }
        if (biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_FROZEN_OCEAN).isPresent()) {
            ((BiomeAccess) (Object) biomeRegistryEntryLookup.getOptional(BiomeKeys.DEEP_FROZEN_OCEAN).get().value()).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_FROZEN_OCEAN);
        }
    }
}
