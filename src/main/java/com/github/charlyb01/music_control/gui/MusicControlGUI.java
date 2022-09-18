package com.github.charlyb01.music_control.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class MusicControlGUI extends LightweightGuiDescription {

    public MusicControlGUI(final MinecraftClient client) {
        WTabPanel tabs = new WTabPanel();
        tabs.add(new PlayPanel(), tab -> tab.title(Text.translatable("gui.music_control.panel.play")));
        tabs.add(new ConfigPanel(), tab -> tab.title(Text.translatable("gui.music_control.panel.config")));
        tabs.add(new OptionPanel(client), tab -> tab.title(Text.translatable("gui.music_control.panel.option")));
        this.setRootPanel(tabs);
    }
}
