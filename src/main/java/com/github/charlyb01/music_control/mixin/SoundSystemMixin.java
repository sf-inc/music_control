package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Shadow @Final private GameOptions settings;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void delayIfNoSound(CallbackInfo ci) {
        if (this.settings.getSoundVolume(SoundCategory.MASTER) <= 0.0F) {
            MusicControlClient.shouldPlay = false;
        }
    }
}
