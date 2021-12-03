package com.github.charlyb01.music_control.config;

import com.github.charlyb01.music_control.categories.MusicCategory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "music_control")
public class ModConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 1, max = 300)
    public int timer = 15;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 15)
    public int musicQueue = 5;

    public boolean displayAtStart = true;
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public DisplayType displayType = DisplayType.JUKEBOX;

    public boolean cheat = false;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public MusicCategory musicCategoryStart = MusicCategory.OVERWORLD;

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
