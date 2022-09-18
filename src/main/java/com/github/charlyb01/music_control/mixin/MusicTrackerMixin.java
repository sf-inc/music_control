package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

            if (!MusicControlClient.loopMusic) {
                if (MusicControlClient.previousMusic) {
                    MusicControlClient.previousMusic = false;
                    Music music = MusicCategories.PLAYED_MUSICS.peekLast();
                    if (music != null) {
                        MusicControlClient.currentMusic = music.getIdentifier();
                    }
                } else if (MusicControlClient.musicSelected != null) {
                    MusicControlClient.currentMusic = MusicControlClient.musicSelected;
                    MusicControlClient.musicSelected = null;
                } else {
                    MusicControlClient.currentMusic = MusicCategories.getMusicIdentifier(this.random);
                }
            }

            SoundEvent soundEvent = type == null || !MusicControlClient.currentCategory.equals(Music.DEFAULT_MUSICS)
                    ? new SoundEvent(MusicControlClient.currentMusic)
                    : type.getSound();
            this.current = PositionedSoundInstance.music(soundEvent);

            if (this.current.getSound() != SoundManager.MISSING_SOUND) {
                this.client.getSoundManager().play(this.current);
            }

            postPlay();
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void handleMusic(CallbackInfo ci) {
        handlePreviousMusicKey();
        handleNextMusicKey();
        handleResumePauseKey();
        handleChangeCategoryKey();
        handleDisplayMusicKey();
    }

    private void postPlay() {
        if (ModConfig.get().displayAtStart || MusicControlClient.categoryChanged) {
            printMusic();
        }

        if (MusicControlClient.categoryChanged) {
            MusicControlClient.categoryChanged = false;
        }

        this.timeUntilNextSong = ModConfig.get().timer * 20;
    }

    private void printMusic() {
        if (this.current == null || this.client.world == null)
            return;

        if (MusicControlClient.isPaused) {
            Utils.print(this.client, Text.translatable("music.paused"));

        } else if (MusicControlClient.categoryChanged) {
            String category = MusicControlClient.currentCategory.toUpperCase().replace('_', ' ') + ": %s";
            Text title = Text.translatable(this.current.getSound().getIdentifier().toString());
            Utils.print(this.client, Text.translatable(category, title));

        } else {
            Text title = Text.translatable(this.current.getSound().getIdentifier().toString());
            Utils.print(this.client, Text.translatable("record.nowPlaying", title));
        }
    }

    private void handlePreviousMusicKey() {
        if (MusicControlClient.previousMusic) {
            if (MusicControlClient.isPaused) {
                MusicControlClient.previousMusic = false;
                printMusic();
            } else {
                MusicCategories.PLAYED_MUSICS.pollLast();
                this.play(null);
            }
        }
    }

    private void handleNextMusicKey() {
        if (MusicControlClient.nextMusic) {
            MusicControlClient.nextMusic = false;
            MusicControlClient.loopMusic = false;

            if (MusicControlClient.isPaused) {
                printMusic();
            } else {
                this.play(this.client.getMusicType());
            }
        }
    }

    private void handleResumePauseKey() {
        if (MusicControlClient.pauseResume) {
            MusicControlClient.pauseResume = false;

            if (MusicControlClient.isPaused) {
                MusicControlClient.isPaused = false;
                this.client.getSoundManager().resumeAll();

                if (this.client.player != null) {
                    Utils.print(this.client, Text.translatable("music.play"));
                }
            } else {
                MusicControlClient.isPaused = true;
                this.client.getSoundManager().pauseAll();

                if (this.client.player != null) {
                    Utils.print(this.client, Text.translatable("music.pause"));
                }
            }
        }
    }

    private void handleChangeCategoryKey() {
        if (MusicControlClient.nextCategory || MusicControlClient.previousCategory) {
            MusicControlClient.categoryChanged = true;
            MusicCategories.changeCategory(MusicControlClient.nextCategory);

            if (MusicControlClient.nextCategory) {
                MusicControlClient.nextCategory = false;
            } else {
                MusicControlClient.previousCategory = false;
            }

            this.play(this.client.getMusicType());
        }
    }

    private void handleDisplayMusicKey() {
        if (MusicControlClient.printMusic) {
            MusicControlClient.printMusic = false;

            printMusic();
        }
    }
}
