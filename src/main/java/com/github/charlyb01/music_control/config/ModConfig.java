package com.github.charlyb01.music_control.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "music_control")
public class ModConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int test = 10;
}
