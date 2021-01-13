package com.github.charlyb01.music_control.client;

import com.github.charlyb01.music_control.config.ModConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MusicControlClient implements ClientModInitializer {
    public static boolean skip = false;
    private static KeyBinding skipMusic;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        skipMusic = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.music_control.skip",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "category.music_control.title"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (skipMusic.wasPressed()) {
                skip = true;
            }
        });
    }
}
