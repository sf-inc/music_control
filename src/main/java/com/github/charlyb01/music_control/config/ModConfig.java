package com.github.charlyb01.music_control.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "music_control")
public class ModConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 0, max = 300)
    public int timer = 15;

    public boolean print = true;

    public boolean cheat = false;

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
