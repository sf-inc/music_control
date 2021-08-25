package com.github.charlyb01.music_control.client;

import com.github.charlyb01.music_control.Utils;
import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.categories.MusicCategory;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.event.SoundEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MusicControlClient implements ClientModInitializer {
    public static boolean init = false;
    public static boolean isPaused = false;
    public static boolean shouldPlay = true;
    public static boolean categoryChanged = false;

    public static Identifier currentMusic;
    public static MusicCategory currentCategory;
    public static String currentSubCategory;

    public static boolean skip = false;
    public static boolean pause = false;
    public static boolean category = false;
    public static boolean random = false;
    public static boolean print = false;

    private static KeyBinding skipMusic;
    private static KeyBinding pauseResume;
    private static KeyBinding changeCat;
    private static KeyBinding randomMusic;
    private static KeyBinding printMusic;
    private static KeyBinding volumeUp;
    private static KeyBinding volumeDown;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        currentCategory = ModConfig.get().musicCategoryStart;
        SoundEvents.SOUNDS_LOADED.register(((soundManager) -> MusicCategories.init(MinecraftClient.getInstance())));

        skipMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.skip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "category.music_control.title"
        ));

        pauseResume = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.pause",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "category.music_control.title"
        ));

        changeCat = KeyBindingHelper.registerKeyBinding(new KeyBinding(
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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (skipMusic.wasPressed()) {
                skip = true;
            }

            while (pauseResume.wasPressed()) {
                pause = true;
            }

            while (changeCat.wasPressed()) {
                category = true;
            }

            while (randomMusic.wasPressed()) {
                if ((client.player != null && client.player.isCreative()) || ModConfig.get().cheat) {
                    random = true;
                }
            }

            while (printMusic.wasPressed()) {
                print = true;
            }

            while (volumeUp.wasPressed()) {
                float volume = client.options.getSoundVolume(SoundCategory.MUSIC);
                volume = Math.min(volume + 0.01F, 1.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                Utils.print(client, new TranslatableText("music.volume", (int) (100 * volume)));
            }

            while (volumeDown.wasPressed()) {
                float volume = client.options.getSoundVolume(SoundCategory.MUSIC);
                volume = Math.max(volume - 0.01F, 0.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                Utils.print(client, new TranslatableText("music.volume", (int) (100 * volume)));
            }
        });
    }
}
