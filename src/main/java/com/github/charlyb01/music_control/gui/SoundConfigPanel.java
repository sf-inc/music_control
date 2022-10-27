package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.config.ModConfig;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WCardPanel;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.Music.*;

public class SoundConfigPanel extends WBox {
    public SoundConfigPanel(final Identifier sound, final boolean isEvent, final int width) {
        super(Axis.VERTICAL);

        ArrayList<Identifier> soundToAdd = new ArrayList<>();
        ArrayList<Identifier> soundToRemove = new ArrayList<>();
        if (isEvent) {
            MUSIC_BY_EVENT.get(sound).forEach((Music music) -> soundToRemove.add(music.getIdentifier()));
            for (Music music : MUSIC_BY_NAMESPACE.get(ALL_MUSICS)) {
                if (!soundToRemove.contains(music.getIdentifier())) {
                    soundToAdd.add(music.getIdentifier());
                }
            }
        } else {
            Music music = Music.getMusicFromIdentifier(sound);
            if (music == null) return;

            soundToRemove.addAll(music.getEvents());
            for (Identifier event : EVENTS) {
                if (!soundToRemove.contains(event)) {
                    soundToAdd.add(event);
                }
            }
        }

        BiConsumer<Identifier, WButton> onAdded = (Identifier soundClicked, WButton button) -> {
            button.setEnabled(false);
            soundToAdd.remove(soundClicked);
            soundToRemove.add(soundClicked);
            if (isEvent) {
                Music music = Music.getMusicFromIdentifier(soundClicked);
                if (music != null) {
                    music.addEvent(sound);
                }
            } else {
                Music music = Music.getMusicFromIdentifier(sound);
                if (music != null) {
                    music.addEvent(soundClicked);
                }
            }
        };
        BiConsumer<Identifier, WButton> onRemoved = (Identifier soundClicked, WButton button) -> {
            button.setEnabled(false);
            soundToRemove.remove(soundClicked);
            soundToAdd.add(soundClicked);
            if (isEvent) {
                Music music = Music.getMusicFromIdentifier(soundClicked);
                if (music != null) {
                    music.removeEvent(sound);
                }
            } else {
                Music music = Music.getMusicFromIdentifier(sound);
                if (music != null) {
                    music.removeEvent(soundClicked);
                }
            }
        };

        ButtonListPanel addListPanel = new ButtonListPanel(soundToAdd, onAdded, width, ModConfig.get().height - 20);
        ButtonListPanel removeListPanel = new ButtonListPanel(soundToRemove, onRemoved, width, ModConfig.get().height - 20);
        WCardPanel listPanel = new WCardPanel();
        listPanel.add(removeListPanel);
        listPanel.add(addListPanel);

        WToggleButton toggleButton = new WToggleButton(Text.translatable("gui.music_control.toggle.removeAdd"));
        toggleButton.setOnToggle((Boolean doAdd) -> {
            if (doAdd) {
                addListPanel.layout();
                listPanel.setSelectedCard(addListPanel);
            } else {
                removeListPanel.layout();
                listPanel.setSelectedCard(removeListPanel);
            }
        });

        this.add(toggleButton);
        this.add(listPanel);
    }
}
