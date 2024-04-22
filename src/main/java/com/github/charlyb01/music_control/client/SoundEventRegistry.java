package com.github.charlyb01.music_control.client;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SoundEventRegistry {
    public static final HashMap<RegistryKey<Biome>, SoundEvent> BIOME_MUSIC_MAP = new HashMap<>();
    public static final HashMap<Identifier, RegistryKey<Biome>> NAME_BIOME_MAP = new HashMap<>();

    public static final Identifier PLAYER_FLYING = new Identifier("music.misc.flying");
    public static final Identifier PLAYER_RIDING = new Identifier("music.misc.riding");
    public static final Identifier TIME_NIGHT = new Identifier("music.misc.night");
    public static final Identifier WEATHER_RAIN = new Identifier("music.misc.rain");
    public static final Identifier WEATHER_THUNDER = new Identifier("music.misc.thunder");

    public static void init() {
        registerMissingBiomes();
        registerMissingDimensions();
        registerMiscellaneous();
    }

    private static void registerMissingBiomes() {
        registerReference("music.overworld.snowy_plains");
        registerReference("music.overworld.ice_spikes");
        registerReference("music.overworld.snowy_taiga");
        registerReference("music.overworld.snowy_beach");

        registerReference("music.overworld.windswept_hills");
        registerReference("music.overworld.windswept_gravelly_hills");
        registerReference("music.overworld.windswept_forest");
        registerReference("music.overworld.taiga");
        registerReference("music.overworld.old_growth_pine_taiga");
        registerReference("music.overworld.old_growth_spruce_taiga");
        registerReference("music.overworld.stony_shore");

        registerReference("music.overworld.plains");
        registerReference("music.overworld.sunflower_plains");
        registerReference("music.overworld.birch_forest");
        registerReference("music.overworld.old_growth_birch_forest");
        registerReference("music.overworld.dark_forest");
        registerReference("music.overworld.mangrove_swamp");
        registerReference("music.overworld.beach");
        registerReference("music.overworld.mushroom_fields");

        registerReference("music.overworld.savanna");
        registerReference("music.overworld.savanna_plateau");
        registerReference("music.overworld.windswept_savanna");
        registerReference("music.overworld.wooded_badlands");
        registerReference("music.overworld.eroded_badlands");

        registerReference("music.overworld.river");
        registerReference("music.overworld.frozen_river");
        registerReference("music.overworld.warm_ocean");
        registerReference("music.overworld.lukewarm_ocean");
        registerReference("music.overworld.ocean");
        registerReference("music.overworld.deep_ocean");
        registerReference("music.overworld.cold_ocean");
        registerReference("music.overworld.deep_cold_ocean");
        registerReference("music.overworld.frozen_ocean");
        registerReference("music.overworld.deep_frozen_ocean");

        registerReference("music.end.the_end");
        registerReference("music.end.end_highlands");
        registerReference("music.end.end_midlands");
        registerReference("music.end.small_end_islands");
        registerReference("music.end.end_barrens");
    }

    private static void registerMissingDimensions() {
        // use "music.game" as overworld
        registerReference("music.nether");
    }

    private static void registerMiscellaneous() {
        registerReference(PLAYER_FLYING);
        registerReference(PLAYER_RIDING);
        registerReference(TIME_NIGHT);
        registerReference(WEATHER_RAIN);
        registerReference(WEATHER_THUNDER);
    }

    private static void registerReference(final String path) {
        final Identifier id = new Identifier(path);
        registerReference(id);
    }

    private static void registerReference(final Identifier id) {
        Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
