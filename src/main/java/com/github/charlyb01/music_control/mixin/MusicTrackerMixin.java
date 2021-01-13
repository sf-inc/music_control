package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MusicTrackerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private int timeUntilNextSong;
    @Shadow private SoundInstance current;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tryToSkip(CallbackInfo ci) {
        if (MusicControlClient.skip) {
            MusicControlClient.skip = false;
            this.client.getSoundManager().stop(this.current);
            this.timeUntilNextSong = 0;
        }
    }
}
