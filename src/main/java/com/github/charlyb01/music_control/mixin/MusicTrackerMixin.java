package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.categories.MusicCategory;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void playMusic(MusicSound type, CallbackInfo ci) {
        if (!MusicControlClient.shouldPlay) {
            MusicControlClient.shouldPlay = true;
            this.timeUntilNextSong = ModConfig.get().timer * 20;
            ci.cancel();
            return;
        }

        if (MusicControlClient.init && this.client.world != null) {
            this.client.getSoundManager().stop(this.current);

            if (MusicControlClient.currentCategory.equals(MusicCategory.DEFAULT)) {
                if (this.timeUntilNextSong > 0 || this.current != null) {
                    this.timeUntilNextSong = 0;
                    this.current = null;
                    ci.cancel();
                }
                return;
            }

            if (!MusicControlClient.loop && !MusicControlClient.currentMusic.equals(MusicControlClient.previousMusic)) {
                MusicControlClient.previousMusic = MusicControlClient.currentMusic;
                MusicControlClient.currentMusic = MusicCategories.getMusicIdentifier(this.random);
            }

            SoundEvent soundEvent = new SoundEvent(MusicControlClient.currentMusic);
            this.current = PositionedSoundInstance.music(soundEvent);

            if (this.current.getSound() != SoundManager.MISSING_SOUND) {
                this.client.getSoundManager().play(this.current);
            }

            if (ModConfig.get().displayAtStart || MusicControlClient.categoryChanged) {
                printMusic();
            }

            if (MusicControlClient.categoryChanged) {
                MusicControlClient.categoryChanged = false;
            }

            this.timeUntilNextSong = ModConfig.get().timer * 20;
            ci.cancel();
        }
    }

    @Inject(method = "play", at = @At("TAIL"))
    private void playDefaultMusic(MusicSound type, CallbackInfo ci) {
        if ((ModConfig.get().displayAtStart || MusicControlClient.categoryChanged)
                && this.client.world != null) {
            printMusic();
        }

        if (MusicControlClient.categoryChanged) {
            MusicControlClient.categoryChanged = false;
        }

        this.timeUntilNextSong = ModConfig.get().timer * 20;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void changeMusic(CallbackInfo ci) {
        if (!ModConfig.get().cheat
                && this.client.player != null
                && !this.client.player.isCreative()) {
                MusicCategories.updateDimension(this.client.world);
        }

        handlePreviousMusicKey();
        handleNextMusicKey();
        handleResumePauseKey();
        handleChangeCategoryKey();
        handleRandomMusicKey();
        handleDisplayMusicKey();
    }

    private void printMusic() {
        if (this.current != null) {
            if (MusicControlClient.isPaused) {
                Utils.print(this.client, new TranslatableText("music.paused"));
            } else if (MusicControlClient.categoryChanged) {
                String category = MusicControlClient.currentCategory.equals(MusicCategory.CUSTOM)
                        ? MusicControlClient.currentCategory + ": " + MusicControlClient.currentSubCategory.toUpperCase().replace('_', ' ')
                        : MusicControlClient.currentCategory.toString();
                Utils.print(this.client, Text.of(category));
            } else {
                TranslatableText title = new TranslatableText(this.current.getSound().getIdentifier().toString());
                Utils.print(this.client, new TranslatableText("record.nowPlaying", title));
            }
        }
    }

    private void handlePreviousMusicKey() {
        if (MusicControlClient.replay) {
            MusicControlClient.replay = false;

            if (MusicControlClient.isPaused) {
                printMusic();
            } else if (MusicControlClient.previousMusic != null) {
                MusicControlClient.currentMusic = MusicControlClient.previousMusic;
                this.play(null);
            }
        }
    }

    private void handleNextMusicKey() {
        if (MusicControlClient.skip) {
            MusicControlClient.skip = false;
            MusicControlClient.loop = false;

            if (MusicControlClient.isPaused) {
                printMusic();
            } else {
                this.play(null);
            }
        }
    }

    private void handleResumePauseKey() {
        if (MusicControlClient.pause) {
            MusicControlClient.pause = false;

            if (MusicControlClient.isPaused) {
                MusicControlClient.isPaused = false;
                this.client.getSoundManager().resumeAll();

                if (this.client.player != null) {
                    Utils.print(this.client, new TranslatableText("music.play"));
                }
            } else {
                MusicControlClient.isPaused = true;
                this.client.getSoundManager().pauseAll();

                if (this.client.player != null) {
                    Utils.print(this.client, new TranslatableText("music.pause"));
                }
            }
        }
    }

    private void handleChangeCategoryKey() {
        if (MusicControlClient.category) {
            MusicControlClient.category = false;
            MusicControlClient.categoryChanged = true;

            MusicCategories.changeCategory(this.client.player);
            this.play(null);
        }
    }

    private void handleRandomMusicKey() {
        if (MusicControlClient.random) {
            MusicControlClient.random = false;
            MusicControlClient.categoryChanged = true;

            if (MusicControlClient.currentCategory != MusicCategory.ALL) {
                MusicControlClient.currentCategory = MusicCategory.ALL;
            }

            this.play(null);
        }
    }

    private void handleDisplayMusicKey() {
        if (MusicControlClient.print) {
            MusicControlClient.print = false;

            printMusic();
        }
    }
}
