package com.github.charlyb01.music_control.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "cosmetics")
public class CosmeticsConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Display display = new Display();

    public static class Display {
        public boolean atMusicStart = true;
        public boolean remainingSeconds = false;
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public DisplayType type = DisplayType.JUKEBOX;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public Gui gui = new Gui();

    public static class Gui {
        @ConfigEntry.BoundedDiscrete(min = 100, max = 250)
        public int height = 150;
        @ConfigEntry.BoundedDiscrete(min = 150, max = 450)
        public int width = 320;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public ScrollSpeed scrollSpeed = ScrollSpeed.NORMAL;
    }
}
