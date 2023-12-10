package com.github.charlyb01.music_control.client;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.imixin.GameOptionsAccess;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.MusicControlGUI;
import com.github.charlyb01.music_control.gui.MusicControlScreen;
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
    private static KeyBinding previousCategory;
    private static KeyBinding nextCategory;
    private static KeyBinding printMusic;
    private static KeyBinding volumeUp;
    private static KeyBinding volumeDown;
    private static KeyBinding openMenu;

    public static void register() {
        registerKeys();
        registerEvents();
    }

    private static void registerKeys() {
        previousMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.previousMusic",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "category.music_control.title"
        ));

        nextMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.nextMusic",
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

        previousCategory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.previousCategory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                "category.music_control.title"
        ));

        nextCategory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.nextCategory",
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

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.openMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.music_control.title"
        ));
    }

    private static void registerEvents() {
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

            while (previousCategory.wasPressed()) {
                MusicControlClient.previousCategory = true;
            }

            while (nextCategory.wasPressed()) {
                MusicControlClient.nextCategory = true;
            }

            while (printMusic.wasPressed()) {
                MusicControlClient.printMusic = true;
            }

            while (volumeUp.wasPressed()) {
                int volume = Math.round(client.options.getSoundVolume(SoundCategory.MUSIC) * 100.F);
                volume = Math.min(volume + ModConfig.get().volumeIncrement, ModConfig.get().allowHighVolume ? 200 : 100);
                ((GameOptionsAccess) client.options).setSoundCategoryVolume(SoundCategory.MUSIC, volume / 100.0);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", volume));
            }

            while (volumeDown.wasPressed()) {
                int volume = Math.round(client.options.getSoundVolume(SoundCategory.MUSIC) * 100.F);
                volume = Math.max(volume - ModConfig.get().volumeIncrement, 0);
                ((GameOptionsAccess) client.options).setSoundCategoryVolume(SoundCategory.MUSIC, volume / 100.0);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", volume));
            }

            while (openMenu.wasPressed()) {
                client.setScreen(new MusicControlScreen(new MusicControlGUI(client)));
            }
        });
    }
}
