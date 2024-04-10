package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.components.LongTextButton;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

import static com.github.charlyb01.music_control.categories.MusicCategories.PLAYED_MUSICS;

public class HistoryPanel extends WBox {
    public HistoryPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        ArrayList<Identifier> musics = new ArrayList<>(PLAYED_MUSICS);
        Collections.reverse(musics);

        BiConsumer<Identifier, LongTextButton> onMusicClicked = (Identifier identifier, LongTextButton button) -> {
            PLAYED_MUSICS.remove(identifier);
            musics.remove(identifier);
        };

        ButtonListPanel playedListPanel = new ButtonListPanel(
                musics,
                onMusicClicked,
                ModConfig.get().cosmetics.gui.width,
                ModConfig.get().cosmetics.gui.height,
                false,
                false);

        this.add(playedListPanel);
    }
}
