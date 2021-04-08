package com.github.charlyb01.music_control.mixin;

import net.minecraft.client.options.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(GameOptions.class)
public interface GameOptionAccessor {
    @Accessor
    Map<SoundCategory, Float> getSoundVolumeLevels();
}
