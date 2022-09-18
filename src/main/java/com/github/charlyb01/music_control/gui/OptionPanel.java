package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.ResourcePackUtils;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class OptionPanel extends WBox {
    public OptionPanel(final MinecraftClient client) {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        WButton saveButton = new WButton(Text.translatable("gui.music_control.button.save"));
        saveButton.setOnClick(() -> {
            ResourcePackUtils.writeConfig();
            client.reloadResources();
        });
        this.add(saveButton, 100, 20);
    }
}
