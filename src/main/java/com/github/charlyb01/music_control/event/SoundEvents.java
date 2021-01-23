package com.github.charlyb01.music_control.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.sound.SoundManager;

@Environment(EnvType.CLIENT)
public final class SoundEvents {
    private SoundEvents() {
    }

    /**
     * Called when Minecraft has loaded sounds.
     *
     * <p>This occurs when the menu screen is displayed. This is one of the last thing the client do during starting.
     */
    public static final Event<SoundsLoaded> SOUNDS_LOADED = EventFactory.createArrayBacked(SoundsLoaded.class, callbacks -> soundManager -> {
        for (SoundsLoaded callback : callbacks) {
            callback.onSoundsLoaded(soundManager);
        }
    });

    @FunctionalInterface
    public interface SoundsLoaded {
        void onSoundsLoaded(SoundManager soundManager);
    }
}