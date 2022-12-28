package com.github.charlyb01.music_control.event;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public abstract class SoundEventList {
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_MENU = SoundEvents.MUSIC_MENU;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DRAGON = SoundEvents.MUSIC_DRAGON;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_END = SoundEvents.MUSIC_END;

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_PLAINS = registerReference("music.overworld.snowy_plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ICE_SPIKES = registerReference("music.overworld.ice_spikes");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_TAIGA = registerReference("music.overworld.snowy_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_BEACH = registerReference("music.overworld.snowy_beach");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_GROVE = SoundEvents.MUSIC_OVERWORLD_GROVE;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_SLOPES = SoundEvents.MUSIC_OVERWORLD_SNOWY_SLOPES;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_JAGGED_PEAKS = SoundEvents.MUSIC_OVERWORLD_JAGGED_PEAKS;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_FROZEN_PEAKS = SoundEvents.MUSIC_OVERWORLD_FROZEN_PEAKS;

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_HILLS = registerReference("music.overworld.windswept_hills");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS = registerReference("music.overworld.windswept_gravelly_hills");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_FOREST = registerReference("music.overworld.windswept_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_TAIGA = registerReference("music.overworld.taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA = registerReference("music.overworld.old_growth_pine_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA = registerReference("music.overworld.old_growth_spruce_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_STONY_SHORE = registerReference("music.overworld.stony_shore");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_PLAINS = registerReference("music.overworld.plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SUNFLOWER_PLAINS = registerReference("music.overworld.sunflower_plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_FOREST = registerReference("music.overworld.forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_FLOWER_FOREST = registerReference("music.overworld.flower_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BIRCH_FOREST = registerReference("music.overworld.birch_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST = registerReference("music.overworld.old_growth_birch_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DARK_FOREST = registerReference("music.overworld.dark_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SWAMP = SoundEvents.MUSIC_OVERWORLD_SWAMP;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MANGROVE_SWAMP = registerReference("music.overworld.mangrove_swamp");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_JUNGLE = registerReference("music.overworld.jungle");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SPARSE_JUNGLE = registerReference("music.overworld.sparse_jungle");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BAMBOO_JUNGLE = registerReference("music.overworld.bamboo_jungle");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BEACH = registerReference("music.overworld.beach");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MUSHROOM_FIELDS = registerReference("music.overworld.mushroom_fields");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MEADOW = SoundEvents.MUSIC_OVERWORLD_MEADOW;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_STONY_PEAKS = SoundEvents.MUSIC_OVERWORLD_STONY_PEAKS;

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DESERT = registerReference("music.overworld.desert");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SAVANNA = registerReference("music.overworld.savanna");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SAVANNA_PLATEAU = registerReference("music.overworld.savanna_plateau");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_SAVANNA = registerReference("music.overworld.windswept_savanna");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BADLANDS = registerReference("music.overworld.badlands");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WOODED_BADLANDS = registerReference("music.overworld.wooded_badlands");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ERODED_BADLANDS = registerReference("music.overworld.eroded_badlands");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_RIVER = registerReference("music.overworld.river");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_FROZEN_RIVER = registerReference("music.overworld.frozen_river");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WARM_OCEAN = registerReference("music.overworld.warm_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_LUKEWARM_OCEAN = registerReference("music.overworld.lukewarm_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OCEAN = registerReference("music.overworld.ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DEEP_OCEAN = registerReference("music.overworld.deep_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_COLD_OCEAN = registerReference("music.overworld.cold_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DEEP_COLD_OCEAN = registerReference("music.overworld.deep_cold_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_FROZEN_OCEAN = registerReference("music.overworld.frozen_ocean");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DEEP_FROZEN_OCEAN = registerReference("music.overworld.deep_frozen_ocean");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DRIPSTONE_CAVES = SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_LUSH_CAVES = SoundEvents.MUSIC_OVERWORLD_LUSH_CAVES;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DEEP_DARK = SoundEvents.MUSIC_OVERWORLD_DEEP_DARK;

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_NETHER_NETHER_WASTES = SoundEvents.MUSIC_NETHER_NETHER_WASTES;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_NETHER_SOUL_SAND_VALLEY = SoundEvents.MUSIC_NETHER_SOUL_SAND_VALLEY;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_NETHER_CRIMSON_FOREST = SoundEvents.MUSIC_NETHER_CRIMSON_FOREST;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_NETHER_WARPED_FOREST = SoundEvents.MUSIC_NETHER_WARPED_FOREST;
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_NETHER_BASALT_DELTAS = SoundEvents.MUSIC_NETHER_BASALT_DELTAS;

    private static RegistryEntry.Reference<SoundEvent> registerReference(final String id) {
        final Identifier identifier = new Identifier(id);
        return Registry.registerReference(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
