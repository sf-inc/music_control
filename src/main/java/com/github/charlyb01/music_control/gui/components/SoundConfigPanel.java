package com.github.charlyb01.music_control.gui.components;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.config.ModConfig;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.Music.*;

public class SoundConfigPanel extends WBox {

    private final WCardPanel cardPanel = new WCardPanel();
    private ButtonListPanel addAnyListPanel;
    private ButtonListPanel removeAnyListPanel;
    private ButtonListPanel addEventListPanel;
    private ButtonListPanel removeEventListPanel;

    public SoundConfigPanel(final Identifier sound, final boolean isEvent, final int width) {
        super(Axis.VERTICAL);

        this.setupBar(isEvent);
        this.setupAnyListPanel(sound, isEvent, width);
        this.setupEventListPanel(sound, width);

        this.cardPanel.add(this.removeAnyListPanel);
        this.cardPanel.add(this.addAnyListPanel);
        this.cardPanel.add(this.removeEventListPanel);
        this.cardPanel.add(this.addEventListPanel);

        this.add(this.cardPanel);
    }

    private void onButtonUpdate(final boolean doAdd, final boolean isEvent, final boolean fromEvent, WLabel label) {
        ButtonListPanel panel;
        if (doAdd) {
            if (isEvent) {
                panel = this.addEventListPanel;
            } else {
                panel = this.addAnyListPanel;
            }
        } else {
            if (isEvent) {
                panel = this.removeEventListPanel;
            } else {
                panel = this.removeAnyListPanel;
            }
        }

        panel.layout();
        this.cardPanel.setSelectedCard(panel);

        Text addText = Text.translatable(doAdd
                ? "gui.music_control.label.add"
                : "gui.music_control.label.remove");
        Text eventText = Text.translatable(!isEvent && fromEvent
                ? "gui.music_control.label.music"
                : "gui.music_control.label.event");
        label.setText(Text.of(addText.getString() + eventText.getString()));
    }

    private void setupBar(final boolean fromEvent) {
        WToggleButton removeAddButton = new WToggleButton() {
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                super.addTooltip(tooltip);
                tooltip.add(Text.translatable("gui.music_control.toggle.removeAdd"));
            }
        };
        WToggleButton musicEventButton = new WToggleButton() {
            @Override
            public void addTooltip(TooltipBuilder tooltip) {
                super.addTooltip(tooltip);
                tooltip.add(Text.translatable("gui.music_control.toggle.musicEvent"));
            }
        };

        Text addText = Text.translatable("gui.music_control.label.remove");
        Text eventText = Text.translatable(fromEvent
                ? "gui.music_control.label.music"
                : "gui.music_control.label.event");
        WLabel label = new WLabel(Text.of(addText.getString() + eventText.getString()));
        label.setVerticalAlignment(VerticalAlignment.CENTER);

        removeAddButton.setOnToggle((Boolean doAdd) -> this.onButtonUpdate(doAdd, musicEventButton.getToggle(), fromEvent, label));
        musicEventButton.setOnToggle((Boolean isEvent) -> this.onButtonUpdate(removeAddButton.getToggle(), isEvent, fromEvent, label));

        WBox buttonBar = new WBox(Axis.HORIZONTAL);
        buttonBar.add(removeAddButton);
        if (fromEvent) buttonBar.add(musicEventButton);
        buttonBar.add(label);

        this.add(buttonBar);
    }

    private void setupAnyListPanel(final Identifier sound, final boolean fromEvent, final int width) {
        ArrayList<Identifier> soundToAdd = new ArrayList<>();
        ArrayList<Identifier> soundToRemove = new ArrayList<>();
        if (fromEvent) {
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
            soundToAdd.addAll(EVENTS);
            for (Identifier eventId : soundToRemove) {
                soundToAdd.remove(eventId);
            }
        }

        BiConsumer<Identifier, Button> onAdded = (Identifier soundClicked, Button button) -> {
            soundToAdd.remove(soundClicked);
            soundToRemove.add(soundClicked);
            soundToRemove.sort(TRANSLATED_ORDER);

            if (fromEvent) {
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
            this.addAnyListPanel.update();
            this.layout();
        };
        BiConsumer<Identifier, Button> onRemoved = (Identifier soundClicked, Button button) -> {
            soundToRemove.remove(soundClicked);
            soundToAdd.add(soundClicked);
            soundToAdd.sort(TRANSLATED_ORDER);

            if (fromEvent) {
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
            this.removeAnyListPanel.update();
            this.layout();
        };

        this.addAnyListPanel = new ButtonListPanel(soundToAdd, onAdded, width, ModConfig.get().cosmetics.gui.height - 20);
        this.removeAnyListPanel = new ButtonListPanel(soundToRemove, onRemoved, width, ModConfig.get().cosmetics.gui.height - 20);
    }

    private void setupEventListPanel(final Identifier sound, final int width) {
        ArrayList<Identifier> soundToAdd = new ArrayList<>(EVENTS);
        ArrayList<Identifier> soundToRemove = new ArrayList<>();

        if (EVENTS_OF_EVENT.containsKey(sound)) {
            soundToRemove.addAll(EVENTS_OF_EVENT.get(sound));
        }
        for (Identifier eventId : soundToRemove) {
            soundToAdd.remove(eventId);
        }

        BiConsumer<Identifier, Button> onAdded = (Identifier soundClicked, Button button) -> {
            soundToAdd.remove(soundClicked);
            soundToRemove.add(soundClicked);
            soundToRemove.sort(TRANSLATED_ORDER);

            if (!EVENTS_OF_EVENT.containsKey(sound)) {
                EVENTS_OF_EVENT.put(sound, new HashSet<>());
            }
            EVENTS_OF_EVENT.get(sound).add(soundClicked);

            this.addEventListPanel.update();
            this.layout();
        };
        BiConsumer<Identifier, Button> onRemoved = (Identifier soundClicked, Button button) -> {
            soundToRemove.remove(soundClicked);
            soundToAdd.add(soundClicked);
            soundToAdd.sort(TRANSLATED_ORDER);

            EVENTS_OF_EVENT.get(sound).remove(soundClicked);
            if (EVENTS_OF_EVENT.get(sound).isEmpty()) {
                EVENTS_OF_EVENT.remove(sound);
            }

            this.removeEventListPanel.update();
            this.layout();
        };

        this.addEventListPanel = new ButtonListPanel(soundToAdd, onAdded, width, ModConfig.get().cosmetics.gui.height - 20);
        this.removeEventListPanel = new ButtonListPanel(soundToRemove, onRemoved, width, ModConfig.get().cosmetics.gui.height - 20);
    }

    @Override
    public void setHost(GuiDescription host) {
        super.setHost(host);
        this.addAnyListPanel.setHost(host);
        this.removeAnyListPanel.setHost(host);
        this.addEventListPanel.setHost(host);
        this.removeEventListPanel.setHost(host);
    }
}
