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
    private static final KeyBinding.Category mainCategory = KeyBinding.Category.create(MusicControlClient.id("main"));

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
                mainCategory
        ));

        nextMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.nextMusic",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                mainCategory
        ));

        pauseResume = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.pause",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                mainCategory
        ));

        loopMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.loop",
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                mainCategory
        ));

        previousCategory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.previousCategory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                mainCategory
        ));

        nextCategory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.nextCategory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_DOWN,
                mainCategory
        ));

        printMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.print",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL,
                mainCategory
        ));

        volumeUp = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.volumeUp",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                mainCategory
        ));

        volumeDown = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.volumeDown",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                mainCategory
        ));

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.openMenu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                mainCategory
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
                int volume = Math.round(client.options.getSoundVolume(SoundCategory.MUSIC) * 100.f);
                volume = Math.min(volume + ModConfig.get().general.misc.volumeIncrement, 100);
                ((GameOptionsAccess) client.options).music_control$setSoundCategoryVolume(SoundCategory.MUSIC, volume / 100.f);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", volume));
            }

            while (volumeDown.wasPressed()) {
                int volume = Math.round(client.options.getSoundVolume(SoundCategory.MUSIC) * 100.f);
                volume = Math.max(volume - ModConfig.get().general.misc.volumeIncrement, 0);
                ((GameOptionsAccess) client.options).music_control$setSoundCategoryVolume(SoundCategory.MUSIC, volume / 100.f);
                client.options.write();
                Utils.print(client, Text.translatable("music.volume", volume));
            }

            while (openMenu.wasPressed()) {
                client.setScreen(new MusicControlScreen(new MusicControlGUI(client)));
            }
        });
    }
}
