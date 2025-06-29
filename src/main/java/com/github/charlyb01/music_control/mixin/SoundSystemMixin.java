package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.imixin.PauseResumeIMixin;
import com.google.common.collect.Multimap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin implements PauseResumeIMixin {
    @Shadow private boolean started;
    @Shadow @Final private GameOptions options;
    @Shadow @Final private Map<SoundInstance, Channel.SourceManager> sources;
    @Shadow @Final private Multimap<SoundCategory, SoundInstance> sounds;

    @Inject(method = "reloadSounds", at = @At("TAIL"))
    private void reinitializeMusicCategories(CallbackInfo ci) {
        MusicCategories.init(MinecraftClient.getInstance());
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void delayIfNoSound(CallbackInfo ci) {
        if (this.options.getSoundVolume(SoundCategory.MASTER) <= 0.0F
                || this.options.getSoundVolume(SoundCategory.MUSIC) <= 0.0F) {
            MusicControlClient.shouldPlay = false;
        }
    }

    @Override
    public void music_control$pauseMusic() {
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
    public void music_control$resumeMusic() {
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
