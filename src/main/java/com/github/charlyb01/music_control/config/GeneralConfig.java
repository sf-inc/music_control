package com.github.charlyb01.music_control.config;

import com.github.charlyb01.music_control.categories.Music;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "general")
public class GeneralConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Timer timer = new Timer();

    public static class Timer {
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean randomDelay = true;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 1800)
        public int maxDelay = 300;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public Fallback fallback = new Fallback();

    public static class Fallback {
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean enabled = true;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean creative = true;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public Misc misc = new Misc();

    public static class Misc {
        public boolean musicDontPause = true;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 1, max = 25)
        public int musicQueue = 10;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public String musicCategoryStart = Music.DEFAULT_MUSICS;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public Volume volume = new Volume();

    public static class Volume {
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int increment = 5;
        public boolean allowHighVolume = false;
    }
}
