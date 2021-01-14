package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.imixin.ISoundSetMixin;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.client.sound.WeightedSoundSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(WeightedSoundSet.class)
public class SoundSetMixin implements ISoundSetMixin {
    @Shadow @Final private List<SoundContainer<Sound>> sounds;

    @Override
    public Integer getSoundsSize() {
        return sounds.size();
    }
}
