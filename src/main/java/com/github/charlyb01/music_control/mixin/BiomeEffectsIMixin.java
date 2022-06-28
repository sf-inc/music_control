package com.github.charlyb01.music_control.mixin;

import net.minecraft.sound.MusicSound;
import net.minecraft.world.biome.BiomeEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(BiomeEffects.class)
public interface BiomeEffectsIMixin {
    @Accessor()
    @Mutable
    void setMusic(Optional<MusicSound> musicSound);
}
