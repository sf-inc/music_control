package com.github.charlyb01.music_control.config;

import com.github.charlyb01.music_control.categories.Music;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "music_control")
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 2)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
    public int timer = 15;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 4)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 25)
    public int musicQueue = 10;

    @ConfigEntry.Category("display")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean displayAtStart = true;

    @ConfigEntry.Category("display")
    @ConfigEntry.Gui.Tooltip(count = 2)
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public DisplayType displayType = DisplayType.JUKEBOX;

    @ConfigEntry.Category("volume")
    @ConfigEntry.Gui.Tooltip(count = 4)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int volumeIncrement = 5;

    @ConfigEntry.Category("volume")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean allowHighVolume = false;

    @ConfigEntry.Category("categories")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public String musicCategoryStart = Music.DEFAULT_MUSICS;

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
