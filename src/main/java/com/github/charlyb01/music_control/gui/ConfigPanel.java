package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.config.ModConfig;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigPanel extends WBox {
    protected static boolean isEvent = false;

    protected WButton hoveredButton;
    protected SoundConfigPanel soundConfigPanel;

    public ConfigPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);
        this.setHorizontalAlignment(HorizontalAlignment.LEFT);

        final WBox self = new WBox(Axis.HORIZONTAL);
        final int outerWidth = ModConfig.get().width;
        final int innerWidth = ModConfig.get().width - 4;

        BiConsumer<Identifier, WButton> onSoundClicked = (Identifier identifier, WButton button) -> {
            if (hoveredButton != null) {
                hoveredButton.setEnabled(true);
            }
            hoveredButton = button;
            hoveredButton.setEnabled(false);

            if (soundConfigPanel != null) {
                self.remove(soundConfigPanel);
            }
            soundConfigPanel = new SoundConfigPanel(identifier, isEvent, innerWidth / 2);
            self.add(soundConfigPanel);
            self.layout();
        };
        Consumer<Boolean> onToggle = (Boolean isEvent) -> {
            ConfigPanel.isEvent = isEvent;

            if (soundConfigPanel != null) {
                self.remove(soundConfigPanel);
                hoveredButton = null;
                soundConfigPanel = null;
            }
        };

        self.add(new SoundListPanel(onSoundClicked, onSoundClicked, onToggle, innerWidth / 2, isEvent));

        this.add(
            self, 
            outerWidth, 
            ModConfig.get().height - 20
        );
    }
}
