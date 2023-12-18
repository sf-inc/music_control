package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.imixin.PauseResumeIMixin;
import com.google.common.collect.Multimap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.*;
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

import java.util.Map;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin implements PauseResumeIMixin {
    @Shadow @Final private GameOptions settings;

    @Shadow private boolean started;

    @Shadow @Final private Map<SoundInstance, Channel.SourceManager> sources;

    @Shadow @Final private Multimap<SoundCategory, SoundInstance> sounds;

    @Shadow protected abstract void tick();

    @Inject(method = "reloadSounds", at = @At("TAIL"))
    private void reinitializeMusicCategories(CallbackInfo ci) {
        MusicCategories.init(MinecraftClient.getInstance());
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void delayIfNoSound(CallbackInfo ci) {
        if (this.settings.getSoundVolume(SoundCategory.MASTER) <= 0.0F
                || this.settings.getSoundVolume(SoundCategory.MUSIC) <= 0.0F) {
            MusicControlClient.shouldPlay = false;
        }
    }

    @Inject(method = "tick(Z)V", at = @At("HEAD"))
    private void alwaysTick(boolean paused, CallbackInfo ci) {
        if (ModConfig.get().musicDontPause && paused) {
            this.tick();
        }
    }

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/sound/SoundInstance;getSound()Lnet/minecraft/client/sound/Sound;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void printRecord(SoundInstance soundInstance, CallbackInfo ci, WeightedSoundSet weightedSoundSet, Identifier identifier, Sound sound) {
        Identifier record = sound.getIdentifier();
        if (identifier.getPath().contains("music_disc")) {
            final MinecraftClient client = MinecraftClient.getInstance();
            client.inGameHud.setRecordPlayingOverlay(Text.translatable(record.toString()));
        }
    }

    @Override
    public void pauseMusic() {
        if (!this.started) {
            return;
        }

        this.sounds.get(SoundCategory.MUSIC).forEach(soundInstance -> {
            Channel.SourceManager sourceManager = this.sources.get(soundInstance);
            if (sourceManager != null) {
                sourceManager.run(Source::pause);
            }
        });
    }

    @Override
    public void resumeMusic() {
        if (!this.started) {
            return;
        }

        this.sounds.get(SoundCategory.MUSIC).forEach(soundInstance -> {
            Channel.SourceManager sourceManager = this.sources.get(soundInstance);
            if (sourceManager != null) {
                sourceManager.run(Source::resume);
            }
        });
    }
}
