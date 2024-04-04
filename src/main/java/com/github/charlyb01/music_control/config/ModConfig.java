package com.github.charlyb01.music_control.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "music_control")
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public GeneralConfig general = new GeneralConfig();

    @ConfigEntry.Category("cosmetics")
    @ConfigEntry.Gui.TransitiveObject
    public CosmeticsConfig cosmetics = new CosmeticsConfig();

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
