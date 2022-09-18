package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
    @Shadow @Final private GameOptions settings;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void delayIfNoSound(CallbackInfo ci) {
        if (this.settings.getSoundVolume(SoundCategory.MASTER) <= 0.0F
                || this.settings.getSoundVolume(SoundCategory.MUSIC) <= 0.0F) {
            MusicControlClient.shouldPlay = false;
        }
    }

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/sound/SoundInstance;getSound()Lnet/minecraft/client/sound/Sound;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void printRecord(SoundInstance soundInstance, CallbackInfo ci, WeightedSoundSet weightedSoundSet, Identifier identifier, Sound sound) {
        Identifier record = sound.getIdentifier();
        if (identifier.getPath().contains("music_disc")) {
            MinecraftClient.getInstance().inGameHud.setRecordPlayingOverlay(Text.translatable(record.toString()));
        }
    }
}
