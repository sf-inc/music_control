package com.github.charlyb01.music_control.event;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public abstract class SoundEventList {
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_PLAINS = registerReference("music.overworld.snowy_plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ICE_SPIKES = registerReference("music.overworld.ice_spikes");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_TAIGA = registerReference("music.overworld.snowy_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SNOWY_BEACH = registerReference("music.overworld.snowy_beach");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_HILLS = registerReference("music.overworld.windswept_hills");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_GRAVELLY_HILLS = registerReference("music.overworld.windswept_gravelly_hills");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_FOREST = registerReference("music.overworld.windswept_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_TAIGA = registerReference("music.overworld.taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_PINE_TAIGA = registerReference("music.overworld.old_growth_pine_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_SPRUCE_TAIGA = registerReference("music.overworld.old_growth_spruce_taiga");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_STONY_SHORE = registerReference("music.overworld.stony_shore");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_PLAINS = registerReference("music.overworld.plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SUNFLOWER_PLAINS = registerReference("music.overworld.sunflower_plains");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BIRCH_FOREST = registerReference("music.overworld.birch_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_OLD_GROWTH_BIRCH_FOREST = registerReference("music.overworld.old_growth_birch_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DARK_FOREST = registerReference("music.overworld.dark_forest");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MANGROVE_SWAMP = registerReference("music.overworld.mangrove_swamp");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_BEACH = registerReference("music.overworld.beach");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MUSHROOM_FIELDS = registerReference("music.overworld.mushroom_fields");

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SAVANNA = registerReference("music.overworld.savanna");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_SAVANNA_PLATEAU = registerReference("music.overworld.savanna_plateau");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WINDSWEPT_SAVANNA = registerReference("music.overworld.windswept_savanna");
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

    private static RegistryEntry.Reference<SoundEvent> registerReference(final String id) {
        final Identifier identifier = new Identifier(id);
        return Registry.registerReference(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}
