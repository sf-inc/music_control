package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.event.SoundLoadedEvent;
import com.github.charlyb01.music_control.imixin.PauseResumeIMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(SoundManager.class)
public class SoundManagerMixin implements PauseResumeIMixin {
    @Shadow @Final private SoundSystem soundSystem;

    @Inject(method = "apply*", at = @At("TAIL"))
    private void onStart(CallbackInfo ci) {
        SoundLoadedEvent.SOUNDS_LOADED.invoker().onSoundsLoaded((SoundManager) (Object) this);
    }

    @Inject(method = "resumeAll", at = @At("TAIL"))
    private void dontResumeIfPaused(CallbackInfo ci) {
        if (MusicControlClient.isPaused) {
            this.pauseMusic();
        }
    }

    @Override
    public void pauseMusic() {
        ((PauseResumeIMixin) this.soundSystem).pauseMusic();
    }

    @Override
    public void resumeMusic() {
        ((PauseResumeIMixin) this.soundSystem).resumeMusic();
    }
}
