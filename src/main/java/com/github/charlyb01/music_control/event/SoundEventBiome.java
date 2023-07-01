package com.github.charlyb01.music_control.event;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.HashMap;

public class SoundEventBiome {
    private static final HashMap<RegistryKey<Biome>, RegistryEntry.Reference<SoundEvent>> BIOME_MUSIC_MAP = new HashMap<>();
    public static void init() {
        BIOME_MUSIC_MAP.put(BiomeKeys.SNOWY_PLAINS, SoundEventList.MUSIC_OVERWORLD_SNOWY_PLAINS);
        BIOME_MUSIC_MAP.put(BiomeKeys.ICE_SPIKES, SoundEventList.MUSIC_OVERWORLD_ICE_SPIKES);
        BIOME_MUSIC_MAP.put(BiomeKeys.SNOWY_TAIGA, SoundEventList.MUSIC_OVERWORLD_SNOWY_TAIGA);
        BIOME_MUSIC_MAP.put(BiomeKeys.SNOWY_BEACH, SoundEventList.MUSIC_OVERWORLD_SNOWY_BEACH);
        BIOME_MUSIC_MAP.put(BiomeKeys.WINDSWEPT_HILLS, SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_HILLS);
        BIOME_MUSIC_MAP.put(BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS);
        BIOME_MUSIC_MAP.put(BiomeKeys.WINDSWEPT_FOREST, SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_FOREST);
        BIOME_MUSIC_MAP.put(BiomeKeys.TAIGA, SoundEventList.MUSIC_OVERWORLD_TAIGA);
        BIOME_MUSIC_MAP.put(BiomeKeys.OLD_GROWTH_PINE_TAIGA, SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA);
        BIOME_MUSIC_MAP.put(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA);
        BIOME_MUSIC_MAP.put(BiomeKeys.STONY_SHORE, SoundEventList.MUSIC_OVERWORLD_STONY_SHORE);
        BIOME_MUSIC_MAP.put(BiomeKeys.PLAINS, SoundEventList.MUSIC_OVERWORLD_PLAINS);
        BIOME_MUSIC_MAP.put(BiomeKeys.SUNFLOWER_PLAINS, SoundEventList.MUSIC_OVERWORLD_SUNFLOWER_PLAINS);
        BIOME_MUSIC_MAP.put(BiomeKeys.BIRCH_FOREST, SoundEventList.MUSIC_OVERWORLD_BIRCH_FOREST);
        BIOME_MUSIC_MAP.put(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, SoundEventList.MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST);
        BIOME_MUSIC_MAP.put(BiomeKeys.DARK_FOREST, SoundEventList.MUSIC_OVERWORLD_DARK_FOREST);
        BIOME_MUSIC_MAP.put(BiomeKeys.MANGROVE_SWAMP, SoundEventList.MUSIC_OVERWORLD_MANGROVE_SWAMP);
        BIOME_MUSIC_MAP.put(BiomeKeys.BEACH, SoundEventList.MUSIC_OVERWORLD_BEACH);
        BIOME_MUSIC_MAP.put(BiomeKeys.MUSHROOM_FIELDS, SoundEventList.MUSIC_OVERWORLD_MUSHROOM_FIELDS);
        BIOME_MUSIC_MAP.put(BiomeKeys.SAVANNA, SoundEventList.MUSIC_OVERWORLD_SAVANNA);
        BIOME_MUSIC_MAP.put(BiomeKeys.SAVANNA_PLATEAU, SoundEventList.MUSIC_OVERWORLD_SAVANNA_PLATEAU);
        BIOME_MUSIC_MAP.put(BiomeKeys.WINDSWEPT_SAVANNA, SoundEventList.MUSIC_OVERWORLD_WINDSWEPT_SAVANNA);
        BIOME_MUSIC_MAP.put(BiomeKeys.WOODED_BADLANDS, SoundEventList.MUSIC_OVERWORLD_WOODED_BADLANDS);
        BIOME_MUSIC_MAP.put(BiomeKeys.ERODED_BADLANDS, SoundEventList.MUSIC_OVERWORLD_ERODED_BADLANDS);
        BIOME_MUSIC_MAP.put(BiomeKeys.RIVER, SoundEventList.MUSIC_OVERWORLD_RIVER);
        BIOME_MUSIC_MAP.put(BiomeKeys.FROZEN_RIVER, SoundEventList.MUSIC_OVERWORLD_FROZEN_RIVER);
        BIOME_MUSIC_MAP.put(BiomeKeys.WARM_OCEAN, SoundEventList.MUSIC_OVERWORLD_WARM_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.LUKEWARM_OCEAN, SoundEventList.MUSIC_OVERWORLD_LUKEWARM_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.OCEAN, SoundEventList.MUSIC_OVERWORLD_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.DEEP_OCEAN, SoundEventList.MUSIC_OVERWORLD_DEEP_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.COLD_OCEAN, SoundEventList.MUSIC_OVERWORLD_COLD_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.DEEP_COLD_OCEAN, SoundEventList.MUSIC_OVERWORLD_DEEP_COLD_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.FROZEN_OCEAN, SoundEventList.MUSIC_OVERWORLD_FROZEN_OCEAN);
        BIOME_MUSIC_MAP.put(BiomeKeys.DEEP_FROZEN_OCEAN, SoundEventList.MUSIC_OVERWORLD_DEEP_FROZEN_OCEAN);

        BiomeModification biomeModification = BiomeModifications.create(
                new Identifier(MusicControlClient.MOD_ID, "add_missing_music_modifier"));
        biomeModification.add(ModificationPhase.POST_PROCESSING,
                (BiomeSelectionContext selectionContext) -> BIOME_MUSIC_MAP.containsKey(selectionContext.getBiomeKey()),
                (BiomeSelectionContext selectionContext, BiomeModificationContext modificationContext) ->
                        modificationContext.getEffects().setMusic(MusicType.createIngameMusic(BIOME_MUSIC_MAP.get(selectionContext.getBiomeKey()))));
    }
}
