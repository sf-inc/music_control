package com.github.charlyb01.music_control.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import net.minecraft.text.Text;

public class MusicControlGUI extends LightweightGuiDescription {
    private static WPanel panel1;
    private static WPanel panel2;

    public MusicControlGUI() {
        panel1 = new WGridPanel();
        panel2 = new WGridPanel();

        WTabPanel tabs = new WTabPanel();
        tabs.add(panel1, tab -> tab.title(Text.translatable("gui.music_control.panel1")));
        tabs.add(panel2, tab -> tab.title(Text.translatable("gui.music_control.panel2")));
        this.setRootPanel(tabs);
    }
}
