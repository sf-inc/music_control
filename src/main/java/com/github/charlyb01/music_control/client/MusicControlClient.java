package com.github.charlyb01.music_control.client;

import com.github.charlyb01.music_control.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MusicControlClient implements ClientModInitializer {
    public static final String MOD_ID = "music_control";

    public static boolean init = false;
    public static boolean inCustomTracking = false;
    public static boolean isPaused = false;
    public static boolean shouldPlay = true;
    public static boolean categoryChanged = false;
    public static Identifier musicSelected;

    public static Identifier currentMusic = Identifier.ofVanilla("current");
    public static String currentCategory;

    public static boolean previousMusic = false;
    public static boolean nextMusic = false;
    public static boolean pauseResume = false;
    public static boolean loopMusic = false;
    public static boolean previousCategory = false;
    public static boolean nextCategory = false;
    public static boolean printMusic = false;

    @Override
    public void onInitializeClient() {
        SoundEventRegistry.init();
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
        MusicKeyBinding.register();

        currentCategory = ModConfig.get().general.misc.musicCategoryStart;
    }
}
