package com.github.charlyb01.music_control.event;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class SoundEventList {
    public static final SoundEvent MUSIC_MENU = SoundEvents.MUSIC_MENU;
    public static final SoundEvent MUSIC_DRAGON = SoundEvents.MUSIC_DRAGON;
    public static final SoundEvent MUSIC_END = SoundEvents.MUSIC_END;

    public static final SoundEvent MUSIC_OVERWORLD_SNOWY_PLAINS = register("music.overworld.snowy_plains");
    public static final SoundEvent MUSIC_OVERWORLD_ICE_SPIKES = register("music.overworld.ice_spikes");
    public static final SoundEvent MUSIC_OVERWORLD_SNOWY_TAIGA = register("music.overworld.snowy_taiga");
    public static final SoundEvent MUSIC_OVERWORLD_SNOWY_BEACH = register("music.overworld.snowy_beach");
    public static final SoundEvent MUSIC_OVERWORLD_GROVE = SoundEvents.MUSIC_OVERWORLD_GROVE;
    public static final SoundEvent MUSIC_OVERWORLD_SNOWY_SLOPES = SoundEvents.MUSIC_OVERWORLD_SNOWY_SLOPES;
    public static final SoundEvent MUSIC_OVERWORLD_JAGGED_PEAKS = SoundEvents.MUSIC_OVERWORLD_JAGGED_PEAKS;
    public static final SoundEvent MUSIC_OVERWORLD_FROZEN_PEAKS = SoundEvents.MUSIC_OVERWORLD_FROZEN_PEAKS;

    public static final SoundEvent MUSIC_OVERWORLD_WINDSWEPT_HILLS = register("music.overworld.windswept_hills");
    public static final SoundEvent MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS = register("music.overworld.windswept_gravelly_hills");
    public static final SoundEvent MUSIC_OVERWORLD_WINDSWEPT_FOREST = register("music.overworld.windswept_forest");
    public static final SoundEvent MUSIC_OVERWORLD_TAIGA = register("music.overworld.taiga");
    public static final SoundEvent MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA = register("music.overworld.old_growth_pine_taiga");
    public static final SoundEvent MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA = register("music.overworld.old_growth_spruce_taiga");
    public static final SoundEvent MUSIC_OVERWORLD_STONY_SHORE = register("music.overworld.stony_shore");

    public static final SoundEvent MUSIC_OVERWORLD_PLAINS = register("music.overworld.plains");
    public static final SoundEvent MUSIC_OVERWORLD_SUNFLOWER_PLAINS = register("music.overworld.sunflower_plains");
    public static final SoundEvent MUSIC_OVERWORLD_FOREST = register("music.overworld.forest");
    public static final SoundEvent MUSIC_OVERWORLD_FLOWER_FOREST = register("music.overworld.flower_forest");
    public static final SoundEvent MUSIC_OVERWORLD_BIRCH_FOREST = register("music.overworld.birch_forest");
    public static final SoundEvent MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST = register("music.overworld.old_growth_birch_forest");
    public static final SoundEvent MUSIC_OVERWORLD_DARK_FOREST = register("music.overworld.dark_forest");
    public static final SoundEvent MUSIC_OVERWORLD_SWAMP = register("music.overworld.swamp");
    public static final SoundEvent MUSIC_OVERWORLD_JUNGLE = register("music.overworld.jungle");
    public static final SoundEvent MUSIC_OVERWORLD_SPARSE_JUNGLE = register("music.overworld.sparse_jungle");
    public static final SoundEvent MUSIC_OVERWORLD_BAMBOO_JUNGLE = register("music.overworld.bamboo_jungle");
    public static final SoundEvent MUSIC_OVERWORLD_BEACH = register("music.overworld.beach");
    public static final SoundEvent MUSIC_OVERWORLD_MUSHROOM_FIELDS = register("music.overworld.mushroom_fields");
    public static final SoundEvent MUSIC_OVERWORLD_MEADOW = SoundEvents.MUSIC_OVERWORLD_MEADOW;
    public static final SoundEvent MUSIC_OVERWORLD_STONY_PEAKS = SoundEvents.MUSIC_OVERWORLD_STONY_PEAKS;

    public static final SoundEvent MUSIC_OVERWORLD_DESERT = register("music.overworld.desert");
    public static final SoundEvent MUSIC_OVERWORLD_SAVANNA = register("music.overworld.savanna");
    public static final SoundEvent MUSIC_OVERWORLD_SAVANNA_PLATEAU = register("music.overworld.savanna_plateau");
    public static final SoundEvent MUSIC_OVERWORLD_WINDSWEPT_SAVANNA = register("music.overworld.windswept_savanna");
    public static final SoundEvent MUSIC_OVERWORLD_BADLANDS = register("music.overworld.badlands");
    public static final SoundEvent MUSIC_OVERWORLD_WOODED_BADLANDS = register("music.overworld.wooded_badlands");
    public static final SoundEvent MUSIC_OVERWORLD_ERODED_BADLANDS = register("music.overworld.eroded_badlands");

    public static final SoundEvent MUSIC_OVERWORLD_RIVER = register("music.overworld.river");
    public static final SoundEvent MUSIC_OVERWORLD_FROZEN_RIVER = register("music.overworld.frozen_river");
    public static final SoundEvent MUSIC_OVERWORLD_WARM_OCEAN = register("music.overworld.warm_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_LUKEWARM_OCEAN = register("music.overworld.lukewarm_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_OCEAN = register("music.overworld.ocean");
    public static final SoundEvent MUSIC_OVERWORLD_DEEP_OCEAN = register("music.overworld.deep_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_COLD_OCEAN = register("music.overworld.cold_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_DEEP_COLD_OCEAN = register("music.overworld.deep_cold_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_FROZEN_OCEAN = register("music.overworld.frozen_ocean");
    public static final SoundEvent MUSIC_OVERWORLD_DEEP_FROZEN_OCEAN = register("music.overworld.deep_frozen_ocean");

    public static final SoundEvent MUSIC_OVERWORLD_DRIPSTONE_CAVES = SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES;
    public static final SoundEvent MUSIC_OVERWORLD_LUSH_CAVES = SoundEvents.MUSIC_OVERWORLD_LUSH_CAVES;

    public static final SoundEvent MUSIC_NETHER_NETHER_WASTES = SoundEvents.MUSIC_NETHER_NETHER_WASTES;
    public static final SoundEvent MUSIC_NETHER_SOUL_SAND_VALLEY = SoundEvents.MUSIC_NETHER_SOUL_SAND_VALLEY;
    public static final SoundEvent MUSIC_NETHER_CRIMSON_FOREST = SoundEvents.MUSIC_NETHER_CRIMSON_FOREST;
    public static final SoundEvent MUSIC_NETHER_WARPED_FOREST = SoundEvents.MUSIC_NETHER_WARPED_FOREST;
    public static final SoundEvent MUSIC_NETHER_BASALT_DELTAS = SoundEvents.MUSIC_NETHER_BASALT_DELTAS;

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(id)));
    }
}
