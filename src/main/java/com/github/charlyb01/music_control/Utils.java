package com.github.charlyb01.music_control;

import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    private Utils() {}

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

    public static float clamp(float num, float min, float max) {
        return Math.min(Math.max(num, min), max);
    }

    public static float roundUp(float value, int places) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.CEILING);
        return bd.floatValue();
    }

    public static float roundDown(float value, int places) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.floatValue();
    }
}
