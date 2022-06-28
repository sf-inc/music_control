package com.github.charlyb01.music_control.event;

import com.github.charlyb01.music_control.access.BiomeAccess;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.BiomeKeys;

public class SoundEventBiome {
    public static void init() {
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_PLAINS)).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_PLAINS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.ICE_SPIKES)).setMusic(SoundEventList.MUSIC_OVERWORLD_ICE_SPIKES);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_TAIGA)).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_TAIGA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SNOWY_BEACH)).setMusic(SoundEventList.MUSIC_OVERWORLD_SNOWY_BEACH);

        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WINDSWEPT_HILLS)).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_HILLS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS)).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WINDSWEPT_FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.TAIGA)).setMusic(SoundEventList.MUSIC_OVERWORLD_TAIGA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.OLD_GROWTH_PINE_TAIGA)).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA)).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.STONY_SHORE)).setMusic(SoundEventList.MUSIC_OVERWORLD_STONY_SHORE);

        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.PLAINS)).setMusic(SoundEventList.MUSIC_OVERWORLD_PLAINS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SUNFLOWER_PLAINS)).setMusic(SoundEventList.MUSIC_OVERWORLD_SUNFLOWER_PLAINS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.FLOWER_FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_FLOWER_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.BIRCH_FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_BIRCH_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.OLD_GROWTH_BIRCH_FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.DARK_FOREST)).setMusic(SoundEventList.MUSIC_OVERWORLD_DARK_FOREST);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SWAMP)).setMusic(SoundEventList.MUSIC_OVERWORLD_SWAMP);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.JUNGLE)).setMusic(SoundEventList.MUSIC_OVERWORLD_JUNGLE);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SPARSE_JUNGLE)).setMusic(SoundEventList.MUSIC_OVERWORLD_SPARSE_JUNGLE);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.BAMBOO_JUNGLE)).setMusic(SoundEventList.MUSIC_OVERWORLD_BAMBOO_JUNGLE);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.BEACH)).setMusic(SoundEventList.MUSIC_OVERWORLD_BEACH);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.MUSHROOM_FIELDS)).setMusic(SoundEventList.MUSIC_OVERWORLD_MUSHROOM_FIELDS);

        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.DESERT)).setMusic(SoundEventList.MUSIC_OVERWORLD_DESERT);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SAVANNA)).setMusic(SoundEventList.MUSIC_OVERWORLD_SAVANNA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.SAVANNA_PLATEAU)).setMusic(SoundEventList.MUSIC_OVERWORLD_SAVANNA_PLATEAU);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WINDSWEPT_SAVANNA)).setMusic(SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_SAVANNA);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.BADLANDS)).setMusic(SoundEventList.MUSIC_OVERWORLD_BADLANDS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WOODED_BADLANDS)).setMusic(SoundEventList.MUSIC_OVERWORLD_WOODED_BADLANDS);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.ERODED_BADLANDS)).setMusic(SoundEventList.MUSIC_OVERWORLD_ERODED_BADLANDS);

        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.RIVER)).setMusic(SoundEventList.MUSIC_OVERWORLD_RIVER);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.FROZEN_RIVER)).setMusic(SoundEventList.MUSIC_OVERWORLD_FROZEN_RIVER);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.WARM_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_WARM_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.LUKEWARM_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_LUKEWARM_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.DEEP_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.COLD_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_COLD_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.DEEP_COLD_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_COLD_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.FROZEN_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_FROZEN_OCEAN);
        ((BiomeAccess) (Object) BuiltinRegistries.BIOME.get(BiomeKeys.DEEP_FROZEN_OCEAN)).setMusic(SoundEventList.MUSIC_OVERWORLD_DEEP_FROZEN_OCEAN);
    }
}
