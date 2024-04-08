package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.components.LongTextButton;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WCardPanel;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.Music.*;

public class SoundConfigPanel extends WBox {

    private ButtonListPanel addListPanel = null;
    private ButtonListPanel removeListPanel = null;

    public SoundConfigPanel(final Identifier sound, final boolean isMusic, final int width) {
        super(Axis.VERTICAL);

        ArrayList<Identifier> soundToAdd = new ArrayList<>();
        ArrayList<Identifier> soundToRemove = new ArrayList<>();
        if (isMusic) {
            Music music = Music.getMusicFromIdentifier(sound);
            if (music == null) {
                return;
            }

            soundToRemove.addAll(music.getEvents());
            for (Identifier event : EVENTS) {
                if (!soundToRemove.contains(event)) {
                    soundToAdd.add(event);
                }
            }
        } else {
            MUSIC_BY_EVENT.get(sound).forEach((Music music) -> soundToRemove.add(music.getIdentifier()));
            for (Music music : MUSIC_BY_NAMESPACE.get(ALL_MUSICS)) {
                if (!soundToRemove.contains(music.getIdentifier())) {
                    soundToAdd.add(music.getIdentifier());
                }
            }
        }

        BiConsumer<Identifier, LongTextButton> onAdded = (Identifier soundClicked, LongTextButton button) -> {
            soundToAdd.remove(soundClicked);
            soundToRemove.add(soundClicked);
            if (isMusic) {
                Music music = Music.getMusicFromIdentifier(sound);
                if (music != null) {
                    music.addEvent(soundClicked);
                }
            } else {
                Music music = Music.getMusicFromIdentifier(soundClicked);
                if (music != null) {
                    music.addEvent(sound);
                }
            }
            this.addListPanel.update();
            this.layout();
        };
        BiConsumer<Identifier, LongTextButton> onRemoved = (Identifier soundClicked, LongTextButton button) -> {
            soundToRemove.remove(soundClicked);
            soundToAdd.add(soundClicked);
            if (isMusic) {
                Music music = Music.getMusicFromIdentifier(sound);
                if (music != null) {
                    music.removeEvent(soundClicked);
                }
            } else {
                Music music = Music.getMusicFromIdentifier(soundClicked);
                if (music != null) {
                    music.removeEvent(sound);
                }
            }
            this.removeListPanel.update();
            this.layout();
        };

        this.addListPanel = new ButtonListPanel(soundToAdd, onAdded, width, ModConfig.get().cosmetics.gui.height - 20, true);
        this.removeListPanel = new ButtonListPanel(soundToRemove, onRemoved, width, ModConfig.get().cosmetics.gui.height - 20, true);
        
        WCardPanel listPanel = new WCardPanel();
        listPanel.add(this.removeListPanel);
        listPanel.add(this.addListPanel);

        WToggleButton toggleButton = new WToggleButton(Text.translatable("gui.music_control.toggle.removeAdd"));
        toggleButton.setOnToggle((Boolean doAdd) -> {
            if (doAdd) {
                this.addListPanel.layout();
                listPanel.setSelectedCard(addListPanel);
            } else {
                this.removeListPanel.layout();
                listPanel.setSelectedCard(removeListPanel);
            }
        });

        this.add(toggleButton);
        this.add(listPanel);
    }

    @Override
    public void setHost(GuiDescription host) {
        super.setHost(host);
        if (this.addListPanel != null) {
            this.addListPanel.setHost(host);
        }
        if (this.removeListPanel != null) {
            this.removeListPanel.setHost(host);
        }
    }
}
