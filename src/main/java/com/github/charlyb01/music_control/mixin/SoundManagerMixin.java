package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.event.SoundLoadedEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(method = "apply", at = @At("TAIL"))
    private void onStart(CallbackInfo ci) {
        SoundLoadedEvent.SOUNDS_LOADED.invoker().onSoundsLoaded((SoundManager) (Object) this);
    }

    @Inject(method = "resumeAll", at = @At("HEAD"), cancellable = true)
    private void dontResumeIfPaused(CallbackInfo ci) {
        if (MusicControlClient.isPaused) {
            ci.cancel();
        }
    }
}
