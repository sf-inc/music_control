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
    public boolean randomTimer = true;

    @ConfigEntry.Category("general")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 1800)
    public int timer = 300;

    @ConfigEntry.Category("general")
    public boolean musicDontPause = true;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 2)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 25)
    public int musicQueue = 10;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean musicFallback = true;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean creativeFallback = true;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 5)
    public String musicCategoryStart = Music.DEFAULT_MUSICS;

    @ConfigEntry.Category("display")
    public boolean displayAtStart = true;

    @ConfigEntry.Category("display")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public DisplayType displayType = DisplayType.JUKEBOX;

    @ConfigEntry.Category("display")
    public boolean displayRemainingSeconds = false;

    @ConfigEntry.Category("volume")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int volumeIncrement = 5;

    @ConfigEntry.Category("volume")
    public boolean allowHighVolume = false;

    @ConfigEntry.Category("gui")
    @ConfigEntry.BoundedDiscrete(min = 100, max = 250)
    public int height = 150;

    @ConfigEntry.Category("gui")
    @ConfigEntry.BoundedDiscrete(min = 150, max = 450)
    public int width = 320;

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
