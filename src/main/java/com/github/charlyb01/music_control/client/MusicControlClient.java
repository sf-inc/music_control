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

    public static Identifier previousMusic;
    public static Identifier currentMusic = new Identifier("current");
    public static MusicCategory currentCategory;
    public static String currentSubCategory;

    public static boolean replay = false;
    public static boolean skip = false;
    public static boolean pause = false;
    public static boolean loop = false;
    public static boolean category = false;
    public static boolean random = false;
    public static boolean print = false;

    private static KeyBinding previous;
    private static KeyBinding next;
    private static KeyBinding pauseResume;
    private static KeyBinding loopMusic;
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

        previous = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.previous",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "category.music_control.title"
        ));

        next = KeyBindingHelper.registerKeyBinding(new KeyBinding(
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
            while (previous.wasPressed()) {
                replay = true;
            }

            while (next.wasPressed()) {
                skip = true;
            }

            while (pauseResume.wasPressed()) {
                pause = true;
            }

            while (loopMusic.wasPressed()) {
                loop = !loop;
                if (loop) {
                    Utils.print(client, new TranslatableText("music.loop.on"));
                } else {
                    Utils.print(client, new TranslatableText("music.loop.off"));
                }
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
                float newVolume = (float) Math.round(volume * 100 + (float) ModConfig.get().volumeIncrement) / 100;
                volume = Math.min(newVolume, ModConfig.get().allowHighVolume ? 2.0F : 1.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                client.options.write();
                Utils.print(client, new TranslatableText("music.volume", Math.round(100 * volume)));
            }

            while (volumeDown.wasPressed()) {
                float volume = client.options.getSoundVolume(SoundCategory.MUSIC);
                float newVolume = (float) Math.round(volume * 100 - (float) ModConfig.get().volumeIncrement) / 100;
                volume = Math.max(newVolume, 0.0F);
                client.options.setSoundVolume(SoundCategory.MUSIC, volume);
                client.options.write();
                Utils.print(client, new TranslatableText("music.volume", Math.round(100 * volume)));
            }
        });
    }
}
