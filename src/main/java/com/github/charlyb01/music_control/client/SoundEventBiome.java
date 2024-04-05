package com.github.charlyb01.music_control.client;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SoundEventBiome {
    public static final HashMap<RegistryKey<Biome>, SoundEvent> BIOME_MUSIC_MAP = new HashMap<>();
    public static final HashMap<Identifier, RegistryKey<Biome>> NAME_BIOME_MAP = new HashMap<>();

    public static void init() {
        registerMissingBiomeSoundEvents();
    }

    private static void registerMissingBiomeSoundEvents() {
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
    }

    private static void registerReference(final String id) {
        final Identifier identifier = new Identifier(id);
        Registry.registerReference(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
