package com.github.charlyb01.music_control.client;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SoundEventRegistry {
    public static final HashMap<RegistryKey<Biome>, SoundEvent> BIOME_MUSIC_MAP = new HashMap<>();
    public static final HashMap<Identifier, RegistryKey<Biome>> NAME_BIOME_MAP = new HashMap<>();

    public static RegistryEntry.Reference<SoundEvent> PLAYER_FLYING = registerReference("music.misc.flying");
    public static RegistryEntry.Reference<SoundEvent> PLAYER_RIDING = registerReference("music.misc.riding");
    public static RegistryEntry.Reference<SoundEvent> TIME_NIGHT = registerReference("music.misc.night");
    public static RegistryEntry.Reference<SoundEvent> WEATHER_RAIN = registerReference("music.misc.rain");
    public static RegistryEntry.Reference<SoundEvent> WEATHER_THUNDER = registerReference("music.misc.thunder");

    public static RegistryEntry.Reference<SoundEvent> SNOWY_PLAINS = registerReference("music.overworld.snowy_plains");
    public static RegistryEntry.Reference<SoundEvent> ICE_SPIKES = registerReference("music.overworld.ice_spikes");
    public static RegistryEntry.Reference<SoundEvent> SNOWY_TAIGA = registerReference("music.overworld.snowy_taiga");
    public static RegistryEntry.Reference<SoundEvent> SNOWY_BEACH = registerReference("music.overworld.snowy_beach");

    public static RegistryEntry.Reference<SoundEvent> WINDSWEPT_HILLS = registerReference("music.overworld.windswept_hills");
    public static RegistryEntry.Reference<SoundEvent> WINDSWEPT_GRAVELLY_HILLS = registerReference("music.overworld.windswept_gravelly_hills");
    public static RegistryEntry.Reference<SoundEvent> WINDSWEPT_FOREST = registerReference("music.overworld.windswept_forest");
    public static RegistryEntry.Reference<SoundEvent> TAIGA = registerReference("music.overworld.taiga");
    public static RegistryEntry.Reference<SoundEvent> OLD_GROWTH_PINE_TAIGA = registerReference("music.overworld.old_growth_pine_taiga");
    public static RegistryEntry.Reference<SoundEvent> OLD_GROWTH_SPRUCE_TAIGA = registerReference("music.overworld.old_growth_spruce_taiga");
    public static RegistryEntry.Reference<SoundEvent> STONY_SHORE = registerReference("music.overworld.stony_shore");

    public static RegistryEntry.Reference<SoundEvent> PLAINS = registerReference("music.overworld.plains");
    public static RegistryEntry.Reference<SoundEvent> SUNFLOWER_PLAINS = registerReference("music.overworld.sunflower_plains");
    public static RegistryEntry.Reference<SoundEvent> BIRCH_FOREST = registerReference("music.overworld.birch_forest");
    public static RegistryEntry.Reference<SoundEvent> OLD_GROWTH_BIRCH_FOREST = registerReference("music.overworld.old_growth_birch_forest");
    public static RegistryEntry.Reference<SoundEvent> DARK_FOREST = registerReference("music.overworld.dark_forest");
    //public static RegistryEntry.Reference<SoundEvent> PALE_GARDEN = registerReference("music.overworld.pale_garden");
    public static RegistryEntry.Reference<SoundEvent> MANGROVE_SWAMP = registerReference("music.overworld.mangrove_swamp");
    public static RegistryEntry.Reference<SoundEvent> BEACH = registerReference("music.overworld.beach");
    public static RegistryEntry.Reference<SoundEvent> MUSHROOM_FIELDS = registerReference("music.overworld.mushroom_fields");

    public static RegistryEntry.Reference<SoundEvent> SAVANNA = registerReference("music.overworld.savanna");
    public static RegistryEntry.Reference<SoundEvent> SAVANNA_PLATEAU = registerReference("music.overworld.savanna_plateau");
    public static RegistryEntry.Reference<SoundEvent> WINDSWEPT_SAVANNA = registerReference("music.overworld.windswept_savanna");
    public static RegistryEntry.Reference<SoundEvent> WOODED_BADLANDS = registerReference("music.overworld.wooded_badlands");
    public static RegistryEntry.Reference<SoundEvent> ERODED_BADLANDS = registerReference("music.overworld.eroded_badlands");

    public static RegistryEntry.Reference<SoundEvent> RIVER = registerReference("music.overworld.river");
    public static RegistryEntry.Reference<SoundEvent> FROZEN_RIVER = registerReference("music.overworld.frozen_river");
    public static RegistryEntry.Reference<SoundEvent> WARM_OCEAN = registerReference("music.overworld.warm_ocean");
    public static RegistryEntry.Reference<SoundEvent> LUKEWARM_OCEAN = registerReference("music.overworld.lukewarm_ocean");
    public static RegistryEntry.Reference<SoundEvent> DEEP_LUKEWARM_OCEAN = registerReference("music.overworld.deep_lukewarm_ocean");
    public static RegistryEntry.Reference<SoundEvent> OCEAN = registerReference("music.overworld.ocean");
    public static RegistryEntry.Reference<SoundEvent> DEEP_OCEAN = registerReference("music.overworld.deep_ocean");
    public static RegistryEntry.Reference<SoundEvent> COLD_OCEAN = registerReference("music.overworld.cold_ocean");
    public static RegistryEntry.Reference<SoundEvent> DEEP_COLD_OCEAN = registerReference("music.overworld.deep_cold_ocean");
    public static RegistryEntry.Reference<SoundEvent> FROZEN_OCEAN = registerReference("music.overworld.frozen_ocean");
    public static RegistryEntry.Reference<SoundEvent> DEEP_FROZEN_OCEAN = registerReference("music.overworld.deep_frozen_ocean");

    public static RegistryEntry.Reference<SoundEvent> THE_END = registerReference("music.end.the_end");
    public static RegistryEntry.Reference<SoundEvent> END_HIGHLANDS = registerReference("music.end.end_highlands");
    public static RegistryEntry.Reference<SoundEvent> END_MIDLANDS = registerReference("music.end.end_midlands");
    public static RegistryEntry.Reference<SoundEvent> SMALL_END_ISLANDS = registerReference("music.end.small_end_islands");
    public static RegistryEntry.Reference<SoundEvent> END_BARRENS = registerReference("music.end.end_barrens");

    public static RegistryEntry.Reference<SoundEvent> NETHER = registerReference("music.nether");

    public static void init() {

    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(final String path) {
        Identifier id = Identifier.ofVanilla(path);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
