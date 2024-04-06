package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.categories.MusicIdentifier;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.imixin.PauseResumeIMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static com.github.charlyb01.music_control.categories.Music.EMPTY_MUSIC;
import static com.github.charlyb01.music_control.categories.Music.EMPTY_MUSIC_ID;

@Mixin(MusicTracker.class)
public abstract class MusicTrackerMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    @Final
    private Random random;
    @Shadow
    private int timeUntilNextSong;
    @Shadow
    private SoundInstance current;

    @Unique
    private boolean displayPrompted = false;

    @Shadow
    public abstract void play(MusicSound type);

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void playMusic(MusicSound type, CallbackInfo ci) {

        final Identifier eventId = type != null ? type.getSound().value().getId() : null;

        MusicControlClient.inCustomTracking = false;

        // do nothing if world is not loaded or controller is not enabled
        if (!MusicControlClient.init || this.client.world == null) {
            return;
        }

        // reset the pending time if it should not play
        if (!MusicControlClient.shouldPlay) {
            MusicControlClient.shouldPlay = true;
            this.timeUntilNextSong = Utils.getTimer(this.random);
            ci.cancel();
            return;
        }

        // stop current music
        boolean wasPlaying = this.client.getSoundManager().isPlaying(this.current);
        this.client.getSoundManager().stop(this.current);

        if (MusicControlClient.musicSelected != null) {
            // a new music is selected from the menu
            MusicControlClient.currentMusic = MusicControlClient.musicSelected;
            MusicControlClient.musicSelected = null;
        } else if (MusicControlClient.previousMusic) {
            // previous music is assigned
            MusicControlClient.previousMusic = false;
            if (wasPlaying) {
                MusicCategories.PLAYED_MUSICS.pollLast();
            }
            Identifier music = MusicCategories.PLAYED_MUSICS.peekLast();
            if (music != null) {
                MusicControlClient.currentMusic = music;
            }
        } else if (MusicControlClient.loopMusic) {
            // loop mode is on
            // do nothing, use the same music
        } else if (eventId != null
                && MusicControlClient.currentCategory.equals(Music.DEFAULT_MUSICS)
                && Music.MUSIC_BY_EVENT.containsKey(eventId)) {
            // normal procedure
            boolean creative = this.client.player != null && this.client.player.isCreative();
            final ArrayList<Music> musics = MusicIdentifier.getListFromEvent(eventId);
            final ArrayList<Music> creativeMusics = MusicIdentifier.getListFromEvent(SoundEvents.MUSIC_CREATIVE.value().getId());

            if (!ModConfig.get().general.fallback.creative && creative) {
                MusicControlClient.currentMusic = MusicIdentifier.getFromList(creativeMusics, this.random);
            } else if (musics.isEmpty()) {
                // this means the current event corresponds to
                // an event with no music.
                if (ModConfig.get().general.fallback.enabled) {
                    // we should fallback on default game/creative music
                    if (creative) {
                        MusicControlClient.currentMusic = creativeMusics.isEmpty()
                                ? EMPTY_MUSIC_ID
                                : MusicIdentifier.getFromList(creativeMusics, this.random);
                    } else {
                        final ArrayList<Music> gameMusics = MusicIdentifier.getListFromEvent(SoundEvents.MUSIC_GAME.value().getId());
                        MusicControlClient.currentMusic = gameMusics.isEmpty()
                                ? EMPTY_MUSIC_ID
                                : MusicIdentifier.getFromList(gameMusics, this.random);
                    }
                } else {
                    // don't play music
                    MusicControlClient.currentMusic = EMPTY_MUSIC_ID;
                }
            } else {
                // play a music from current event
                MusicControlClient.currentMusic = MusicIdentifier.getFromList(musics, this.random);
            }
        } else {
            // just randomly pick one
            // from current category
            MusicControlClient.currentMusic = MusicIdentifier.getFromCategory(this.random);
        }

        // music in no event and no/default namespace
        // should try play with default player
        this.current = PositionedSoundInstance.music(MusicControlClient.currentMusic == null && type != null
                ? type.getSound().value()
                : SoundEvent.of(MusicControlClient.currentMusic));

        if (!this.current.getId().equals(SoundManager.MISSING_SOUND.getIdentifier())) {
            this.client.getSoundManager().play(this.current);
            MusicControlClient.inCustomTracking = true;
        }

        displayMusic();

        this.timeUntilNextSong = Utils.getTimer(this.random);
        ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void handleMusic(CallbackInfo ci) {
        handlePreviousMusicKey();
        handleNextMusicKey();
        handleResumePauseKey();
        handleChangeCategoryKey();
        handleDisplayMusicKey();

        if (MusicControlClient.inCustomTracking) {
            if (this.client == null // no client
                    || !MusicControlClient.init || this.client.world == null // not in game
                    || this.current == null || !this.client.getSoundManager().isPlaying(this.current)) { // current stopped
                MusicControlClient.inCustomTracking = false;
            } else {
                // The music in playing could be forcedly replaced by the original tracker
                // if `this.client.getMusicType().shouldReplaceCurrentMusic()` is true.
                // This causes the absence of custom music in some cases (like in the end).
                ci.cancel();
            }
        }

        // Stop decrementing if music paused
        if (MusicControlClient.isPaused &&
                (this.current == null
                || (this.client != null && !this.client.getSoundManager().isPlaying(this.current)))) {
            this.timeUntilNextSong++;
        }
    }

    @Unique
    private void displayMusic() {
        if (ModConfig.get().cosmetics.display.atMusicStart || MusicControlClient.categoryChanged) {
            printMusic();
        }

        if (MusicControlClient.categoryChanged) {
            MusicControlClient.categoryChanged = false;
        }
    }

    @Unique
    private void printPaused() {
        Utils.print(this.client, Text.translatable("music.paused"));
    }

    @Unique
    private void printMusic() {
        if (this.client.world == null)
            return;

        final String currentMusic = this.current == null || this.current.getSound() == null
                ? EMPTY_MUSIC
                : this.current.getSound().getIdentifier().toString();
        if (currentMusic.equals(EMPTY_MUSIC)) {
            if (!this.displayPrompted) {
                return;
            }
            this.displayPrompted = false;

            if (ModConfig.get().cosmetics.display.remainingSeconds) {
                double remaining = this.timeUntilNextSong / 20.0;
                Utils.print(this.client, Text.translatable("music.no_playing_with_time", String.valueOf(remaining)));
            } else {
                Utils.print(this.client, Text.translatable("music.no_playing"));
            }
        } else {
            Text category = Text.translatableWithFallback("music.category." + MusicControlClient.currentCategory,
                    MusicControlClient.currentCategory.toUpperCase().replace('_', ' '));
            Text music = Text.translatable(currentMusic);
            Text content = MusicControlClient.categoryChanged
                ? Text.translatable("music.format.category", category, music)
                : music;
            Utils.print(this.client, Text.translatable("record.nowPlaying", content));
        }
    }

    // key pressed event handlers

    @Unique
    private void handlePreviousMusicKey() {
        if (MusicControlClient.previousMusic) {
            if (MusicControlClient.isPaused) {
                MusicControlClient.previousMusic = false;
                printPaused();
            } else {
                this.displayPrompted = ModConfig.get().cosmetics.display.atMusicStart;
                this.play(null);
            }
        }
    }

    @Unique
    private void handleNextMusicKey() {
        if (MusicControlClient.nextMusic) {
            MusicControlClient.nextMusic = false;
            MusicControlClient.loopMusic = false;

            if (MusicControlClient.isPaused) {
                printPaused();
            } else {
                this.displayPrompted = ModConfig.get().cosmetics.display.atMusicStart;
                this.play(this.client.getMusicType());
            }
        }
    }

    @Unique
    private void handleResumePauseKey() {
        if (MusicControlClient.pauseResume) {
            MusicControlClient.pauseResume = false;

            if (MusicControlClient.isPaused) {
                MusicControlClient.isPaused = false;
                ((PauseResumeIMixin) this.client.getSoundManager()).music_control$resumeMusic();

                if (this.client.player != null) {
                    Utils.print(this.client, Text.translatable("music.play"));
                }
            } else {
                MusicControlClient.isPaused = true;
                ((PauseResumeIMixin) this.client.getSoundManager()).music_control$pauseMusic();

                if (this.client.player != null) {
                    Utils.print(this.client, Text.translatable("music.pause"));
                }
            }
        }
    }

    @Unique
    private void handleChangeCategoryKey() {
        if (MusicControlClient.nextCategory || MusicControlClient.previousCategory) {
            if (MusicControlClient.isPaused) {
                printPaused();
            } else {
                MusicControlClient.categoryChanged = true;
                MusicCategories.changeCategory(MusicControlClient.nextCategory);
                this.play(this.client.getMusicType());
            }

            if (MusicControlClient.nextCategory) {
                MusicControlClient.nextCategory = false;
            } else {
                MusicControlClient.previousCategory = false;
            }
        }
    }

    @Unique
    private void handleDisplayMusicKey() {
        if (MusicControlClient.printMusic) {
            MusicControlClient.printMusic = false;
            this.displayPrompted = true;

            printMusic();
        }
    }
}
