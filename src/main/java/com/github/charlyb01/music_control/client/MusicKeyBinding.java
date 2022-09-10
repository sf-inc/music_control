package com.github.charlyb01.music_control.client;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.config.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MusicKeyBinding {
    private static KeyBinding previousMusic;
    private static KeyBinding nextMusic;
    private static KeyBinding pauseResume;
    private static KeyBinding loopMusic;
    private static KeyBinding changeCategory;
    private static KeyBinding randomMusic;
    private static KeyBinding printMusic;
    private static KeyBinding volumeUp;
    private static KeyBinding volumeDown;

    public static void init() {
        previousMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.previous",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "category.music_control.title"
        ));

        nextMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.next",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "category.music_control.title"
        ));

        pauseResume = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.pause",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.music_control.title"
        ));

        loopMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.loop",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                "category.music_control.title"
        ));

        changeCategory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.category",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                "category.music_control.title"
        ));

        randomMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.random",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_DOWN,
                "category.music_control.title"
        ));

        printMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.print",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                "category.music_control.title"
        ));

        volumeUp = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.volumeUp",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                "category.music_control.title"
        ));

        volumeDown = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.volumeDown",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                "category.music_control.title"
        ));
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (previousMusic.wasPressed()) {
                MusicControlClient.previousMusic = true;
            }

            while (nextMusic.wasPressed()) {
                MusicControlClient.nextMusic = true;
            }

            while (pauseResume.wasPressed()) {
                MusicControlClient.pauseResume = true;
            }

            while (loopMusic.wasPressed()) {
                MusicControlClient.loopMusic = !MusicControlClient.loopMusic;
                if (MusicControlClient.loopMusic) {
                    Utils.print(client, Text.translatable("music.loop.on"));
                } else {
                    Utils.print(client, Text.translatable("music.loop.off"));
                }
            }

            while (changeCategory.wasPressed()) {
                MusicControlClient.changeCategory = true;
            }

            while (randomMusic.wasPressed()) {
                if ((client.player != null && client.player.isCreative()) || ModConfig.get().cheat) {
                    MusicControlClient.randomMusic = true;
                }
            }

            while (printMusic.wasPressed()) {
                MusicControlClient.printMusic = true;
            }

            while (volumeUp.wasPressed()) {
                float volume = client.options.getSoundVolume(SoundCategory.MUSIC);
                volume = Math.min(volume + (ModConfig.get().volumeIncrement / 100.F), ModConfig.get().allowHighVolume ? 2.0F : 1.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", Math.round(100.F * volume)));
            }

            while (volumeDown.wasPressed()) {
                float volume = client.options.getSoundVolume(SoundCategory.MUSIC);
                volume = Math.max(volume - (ModConfig.get().volumeIncrement / 100.F), 0.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", Math.round(100.F * volume)));
            }
        });
    }
}
