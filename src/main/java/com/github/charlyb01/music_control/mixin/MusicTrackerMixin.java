package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.categories.MusicCategory;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.MusicSound;
import net.minecraft.text.Text;
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

    @Inject(at = @At("HEAD"), method = "play", cancellable = true)
    private void playMusic (MusicSound type, CallbackInfo ci) {
        if (MusicControlClient.init) {
            this.client.getSoundManager().stop(this.current);

            Identifier identifier = MusicCategories.chooseIdentifier(this.random);
            MusicSound musicSound = new MusicSound(Registry.SOUND_EVENT.get(identifier),
                    ModConfig.get().timer * 20, ModConfig.get().timer * 20, true);

            this.current = PositionedSoundInstance.music(musicSound.getSound());
            if (this.current.getSound() != SoundManager.MISSING_SOUND) {
                this.client.getSoundManager().play(this.current);
            }

            if (ModConfig.get().print) {
                printMusic();
            }

            this.timeUntilNextSong = ModConfig.get().timer * 20;
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void changeMusic (CallbackInfo ci) {
        if (MusicControlClient.skip) {
            MusicControlClient.skip = false;

            this.play(null);
        }
        if (MusicControlClient.pause) {
            MusicControlClient.pause = false;

            if (MusicControlClient.isPaused) {
                this.client.getSoundManager().resumeAll();
            } else {
                this.client.getSoundManager().pauseAll();
            }
            MusicControlClient.isPaused = !MusicControlClient.isPaused;
        }
        if (MusicControlClient.category) {
            MusicControlClient.category = false;

            MusicCategories.changeCategory(this.random);
            this.play(null);
        }
        if (MusicControlClient.random) {
            MusicControlClient.random = false;

            MusicControlClient.currentCategory = MusicCategory.ALL;
            this.play(null);
        }
        if (MusicControlClient.print) {
            MusicControlClient.print = false;

            printMusic();
        }
    }

    private void printMusic() {
        if (this.client.player != null && this.current != null) {
            this.client.player.sendMessage(Text.of(MusicControlClient.currentCategory.toString() +
                    ": " + this.current.getSound().getIdentifier()), true);
        }
    }
}
