package com.github.charlyb01.music_control.access;

import net.minecraft.sound.SoundCategory;

public interface GameOptionsAccess {
    void setSoundCategoryVolume(SoundCategory soundCategory, double volume);
}
