package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.imixin.PauseResumeIMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SoundManager.class)
public class SoundManagerMixin implements PauseResumeIMixin {
    @Shadow @Final private SoundSystem soundSystem;

    @Inject(method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Lnet/minecraft/client/sound/SoundManager$SoundList;", at = @At("HEAD"))
    private void resetEventsOfEvent(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<SoundManager.SoundList> cir) {
        Music.EVENTS_OF_EVENT.clear();
    }

    @Inject(method = "resumeAll", at = @At("TAIL"))
    private void dontResumeIfPaused(CallbackInfo ci) {
        if (MusicControlClient.isPaused) {
            this.music_control$pauseMusic();
        }
    }

    @Override
    public void music_control$pauseMusic() {
        ((PauseResumeIMixin) this.soundSystem).music_control$pauseMusic();
    }

    @Override
    public void music_control$resumeMusic() {
        ((PauseResumeIMixin) this.soundSystem).music_control$resumeMusic();
    }
}
