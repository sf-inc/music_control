package com.github.charlyb01.music_control;

import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class Utils {
    private Utils() {}

    public static void print(final MinecraftClient client, final Text text) {
        switch (ModConfig.get().cosmetics.display.type) {
            case JUKEBOX -> client.inGameHud.setOverlayMessage(text, true);
            case ACTION_BAR -> client.inGameHud.setOverlayMessage(text, false);
            case CHAT -> {
                if (client.player != null) {
                    client.player.sendMessage(text, false);
                }
            }
        }
    }

    public static boolean isNight(final World world) {
        long time = world.getTimeOfDay() % 24000L;
        return time > 13000L && time < 23000L;
    }
}
