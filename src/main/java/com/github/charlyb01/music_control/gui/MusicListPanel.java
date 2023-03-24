package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.config.ModConfig;
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
    public MusicListPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        ArrayList<Identifier> musics = new ArrayList<>(PLAYED_MUSICS);
        Collections.reverse(musics);

        BiConsumer<Identifier, WButton> onMusicClicked = (Identifier identifier, WButton button) -> {
            PLAYED_MUSICS.remove(identifier);
            musics.remove(identifier);
        };

        ButtonListPanel playedListPanel = new ButtonListPanel(
                musics,
                onMusicClicked,
                ModConfig.get().width,
                ModConfig.get().height - 20,
                true);
        WText text = new WText(Text.translatable("gui.music_control.text.played_music"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);

        this.add(text, ModConfig.get().width, 20);
        this.add(playedListPanel);
    }
}
