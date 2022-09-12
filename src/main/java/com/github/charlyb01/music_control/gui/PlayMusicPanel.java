package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.client.MusicControlClient;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.Music.ALL_MUSICS;
import static com.github.charlyb01.music_control.categories.Music.MUSIC_LISTS;

public class PlayMusicPanel extends WBox {
    private final static Text NONE_TEXT = Text.translatable("music.none");
    private final static String MUSIC_SELECTED_KEY = "gui.music_control.label.musicSelected";

    public PlayMusicPanel() {
        super(Axis.VERTICAL);
        WLabel musicSelected = new WLabel(Text.translatable(MUSIC_SELECTED_KEY, NONE_TEXT));
        musicSelected.setHorizontalAlignment(HorizontalAlignment.CENTER);

        BiConsumer<Identifier, WButton> configurator = (Identifier identifier, WButton button) -> {
            Text music = Text.translatable(identifier.toString());
            button.setLabel(music);
            button.setOnClick(() -> {
                if (identifier.equals(MusicControlClient.musicSelected)) {
                    MusicControlClient.nextMusic = false;
                    MusicControlClient.musicSelected = null;
                    musicSelected.setText(Text.translatable(MUSIC_SELECTED_KEY, NONE_TEXT));
                } else {
                    MusicControlClient.nextMusic = true;
                    MusicControlClient.musicSelected = identifier;
                    musicSelected.setText(Text.translatable(MUSIC_SELECTED_KEY, music));
                }
            });
        };

        ArrayList<Identifier> data = new ArrayList<>(MUSIC_LISTS.get(ALL_MUSICS).size());
        for (Music music : MUSIC_LISTS.get(ALL_MUSICS)) {
            data.add(music.getIdentifier());
        }

        WListPanel<Identifier, WButton> listPanel = new WListPanel<>(data, WButton::new, configurator);
        this.add(listPanel, 300, 130);
        this.add(musicSelected, 300, 20);
    }
}
