package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MusicTracker.class)
public abstract class MusicTrackerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private Random random;
    @Shadow private int timeUntilNextSong;
    @Shadow private SoundInstance current;

    @Shadow public abstract void play(MusicSound type);

    @Inject(at = @At("HEAD"), method = "tick")
    private void tryToSkip(CallbackInfo ci) {
        if (MusicControlClient.skip) {
            MusicControlClient.skip = false;
            this.client.getSoundManager().stop(this.current);
            this.timeUntilNextSong = 0;
        }
        if (MusicControlClient.category) {
            MusicControlClient.category = false;
            MusicCategories.changeCategory(this.random);

            Identifier identifier = MusicCategories.chooseIdentifier(this.random);
            MusicSound musicSound = new MusicSound(Registry.SOUND_EVENT.get(identifier),
                    ModConfig.get().timer, ModConfig.get().timer, true);

            this.client.getSoundManager().stop(this.current);
            this.timeUntilNextSong = 0;
            play(musicSound);
        }
    }
}
