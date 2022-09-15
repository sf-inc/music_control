package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.client.MusicControlClient;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.Music.*;

public class PlayPanel extends WBox {
    private final static Text NONE_TEXT = Text.translatable("music.none");
    private final static String SELECTED_KEY = "gui.music_control.label.selected";

    public PlayPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        WLabel selected = new WLabel(Text.translatable(SELECTED_KEY, NONE_TEXT));
        selected.setHorizontalAlignment(HorizontalAlignment.CENTER);

        BiConsumer<Identifier, WButton> configurator = (Identifier identifier, WButton button) -> {
            Text sound = Text.translatable(identifier.toString());
            button.setLabel(sound);
            button.setOnClick(() -> {
                if (identifier.equals(MusicControlClient.musicSelected)) {
                    MusicControlClient.nextMusic = false;
                    MusicControlClient.musicSelected = null;
                    selected.setText(Text.translatable(SELECTED_KEY, NONE_TEXT));
                } else {
                    MusicControlClient.nextMusic = true;
                    MusicControlClient.musicSelected = identifier;
                    selected.setText(Text.translatable(SELECTED_KEY, sound));
                }
            });
        };

        ArrayList<Identifier> musics = new ArrayList<>(MUSIC_BY_NAMESPACE.get(ALL_MUSICS).size());
        for (Music music : MUSIC_BY_NAMESPACE.get(ALL_MUSICS)) {
            musics.add(music.getIdentifier());
        }

        WListPanel<Identifier, WButton> musicListPanel = new WListPanel<>(musics, WButton::new, configurator);
        WListPanel<Identifier, WButton> eventListPanel = new WListPanel<>(EVENTS, WButton::new, configurator);
        WCardPanel listPanel = new WCardPanel();
        listPanel.add(musicListPanel);
        listPanel.add(eventListPanel);

        WToggleButton toggleButton = new WToggleButton(Text.translatable("gui.music_control.toggle.musicEvent"));
        toggleButton.setOnToggle((Boolean isEvent) -> {
            if (isEvent) {
                listPanel.setSelectedCard(eventListPanel);
            } else {
                listPanel.setSelectedCard(musicListPanel);
            }
        });

        this.add(toggleButton);
        this.add(listPanel, 300, 130);
        this.add(selected, 300, 20);
    }
}
