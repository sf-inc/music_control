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
    public Event event = new Event();

    public static class Event {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean creativeEventFallback = true;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public DimensionEventChance dimensionEventChance = DimensionEventChance.PROPORTIONAL;
        @ConfigEntry.Gui.Tooltip(count = 3)
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public MiscEventChance miscEventChance = MiscEventChance.PROPORTIONAL;
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
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int volumeIncrement = 5;
    }
}
