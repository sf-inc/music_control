package com.github.charlyb01.music_control.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import net.minecraft.text.Text;

public class MusicControlGUI extends LightweightGuiDescription {

    public MusicControlGUI() {
        WTabPanel tabs = new WTabPanel();
        tabs.add(new PlayMusicPanel(), tab -> tab.title(Text.translatable("gui.music_control.panel.play")));
        this.setRootPanel(tabs);
    }
}
