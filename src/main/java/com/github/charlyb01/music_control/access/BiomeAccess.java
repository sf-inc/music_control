package com.github.charlyb01.music_control.access;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

public interface BiomeAccess {
    void setMusic(RegistryEntry.Reference<SoundEvent> soundEvent);
}
