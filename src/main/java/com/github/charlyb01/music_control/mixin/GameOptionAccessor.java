package com.github.charlyb01.music_control.mixin;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface GameOptionAccessor {
    @Accessor
    Object2FloatMap<SoundCategory> getSoundVolumeLevels();
}