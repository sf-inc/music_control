package com.github.charlyb01.music_control;

import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class Utils {
    private Utils() {}

    public static int getTimer(final Random random) {
        ModConfig config = ModConfig.get();
        return config.randomTimer
                ? MathHelper.nextInt(random, config.timer * 10, config.timer * 20)
                : config.timer * 20;
    }

    public static void print(final MinecraftClient client, final Text text) {
        switch (ModConfig.get().displayType) {
            case JUKEBOX -> client.inGameHud.setOverlayMessage(text, true);
            case ACTION_BAR -> client.inGameHud.setOverlayMessage(text, false);
            case CHAT -> {
                if (client.player != null) {
                    client.player.sendMessage(text, false);
                }
            }
        }
    }
}
