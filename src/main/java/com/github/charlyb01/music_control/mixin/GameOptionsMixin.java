package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.imixin.GameOptionsAccess;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements GameOptionsAccess {
    @Shadow @Final private Map<SoundCategory, SimpleOption<Double>> soundVolumeLevels;

    @Override
    public void setSoundCategoryVolume(final SoundCategory soundCategory, final double volume) {
        SimpleOption<Double> simpleOption = this.soundVolumeLevels.get(soundCategory);
        if (simpleOption != null) {
            simpleOption.setValue(volume);
        }
    }
}
