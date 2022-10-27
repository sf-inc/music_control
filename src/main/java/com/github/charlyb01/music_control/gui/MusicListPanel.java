package com.github.charlyb01.music_control.gui;

import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.MusicCategories.PLAYED_MUSICS;

public class MusicListPanel extends WBox {
    protected static final int HEIGHT = 125;

    public MusicListPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        ArrayList<Identifier> musics = new ArrayList<>(PLAYED_MUSICS);
        Collections.reverse(musics);

        BiConsumer<Identifier, WButton> onMusicClicked = (Identifier identifier, WButton button) -> {
            PLAYED_MUSICS.remove(identifier);
            musics.remove(identifier);
        };

        ButtonListPanel playedListPanel = new ButtonListPanel(musics, onMusicClicked, 300, HEIGHT);
        WText text = new WText(Text.translatable("gui.music_control.text.played_music"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);

        this.add(text, 300, 20);
        this.add(playedListPanel);
    }
}
