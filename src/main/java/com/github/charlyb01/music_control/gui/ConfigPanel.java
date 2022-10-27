package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.config.ModConfig;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigPanel extends WBox {
    protected static boolean isEvent = false;

    protected WButton hoveredButton;
    protected SoundConfigPanel soundConfigPanel;

    public ConfigPanel() {
        super(Axis.HORIZONTAL);
        this.setInsets(Insets.ROOT_PANEL);

        BiConsumer<Identifier, WButton> onSoundClicked = (Identifier identifier, WButton button) -> {
            if (hoveredButton != null) {
                hoveredButton.setEnabled(true);
            }
            hoveredButton = button;
            hoveredButton.setEnabled(false);

            if (soundConfigPanel != null) {
                this.remove(soundConfigPanel);
            }
            soundConfigPanel = new SoundConfigPanel(identifier, isEvent, ModConfig.get().width / 2);
            this.add(soundConfigPanel);
            this.layout();
        };
        Consumer<Boolean> onToggle = (Boolean isEvent) -> {
            ConfigPanel.isEvent = isEvent;

            if (soundConfigPanel != null) {
                this.remove(soundConfigPanel);
                hoveredButton = null;
                soundConfigPanel = null;
            }
        };

        this.add(new SoundListPanel(onSoundClicked, onSoundClicked, onToggle, ModConfig.get().width / 2, isEvent));
    }
}
