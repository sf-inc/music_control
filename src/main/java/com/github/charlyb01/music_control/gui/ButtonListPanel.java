package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.github.charlyb01.music_control.gui.components.TextFilter;
import com.github.charlyb01.music_control.gui.components.WFilterListPanel;

public class ButtonListPanel extends WBox {

    private final WFilterListPanel<Identifier, WButton> items;
    private final static int FILTER_HEIGHT = 20;

    public ButtonListPanel(
            final List<Identifier> data,
            final BiConsumer<Identifier, WButton> onClicked,
            final int width,
            final int height,
            final boolean withFilter) {
        super(Axis.VERTICAL);

        BiConsumer<Identifier, WButton> configurator = (Identifier identifier, WButton button) -> {
            button.setLabel(Text.of(Music.getTranslatedText(identifier).asTruncatedString(width / 7)));
            button.setOnClick(() -> onClicked.accept(identifier, button));
        };
        this.items = new WFilterListPanel<>(data, WButton::new, configurator);

        if (withFilter && height > FILTER_HEIGHT) {
            TextFilter filter = new TextFilter(this::runFilter, width, FILTER_HEIGHT);
            this.add(filter, width, FILTER_HEIGHT);
            this.add(this.items, width, height - FILTER_HEIGHT);
            this.layout();
        } else {
            this.add(this.items, width, height);
        }
    }

    public void runFilter(String s) {
        if (s == null || s.isEmpty()) {
            this.items.runFilter(null);
            return;
        }

        String[] wordsRaw = s.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String word : wordsRaw) {
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        final boolean andFlag = !words.isEmpty() && words.get(0).equalsIgnoreCase("/and");
        if (andFlag) {
            words.remove(0);
        }

        this.items.runFilter((id) -> {
            String raw = id.toString();
            String evaluated = Text.translatable(raw).getString();
            String evaluatedLower = evaluated.toLowerCase();
            String path = id.getPath();
            String namespace = id.getNamespace();

            for (String word : words) {
                boolean matches;
                if (word.charAt(0) == '@') {
                    matches = namespace.contains(word.substring(1));
                } else if (word.charAt(0) == '#') {
                    matches = path.contains(word.substring(1));
                } else if (word.charAt(0) == '$') {
                    matches = raw.contains(word.substring(1));
                } else if (word.charAt(0) == '!') {
                    matches = evaluated.contains(word.substring(1));
                } else {
                    matches = evaluatedLower.contains(word.toLowerCase());
                }

                if (matches && !andFlag) {
                    return true;
                } else if (!matches && andFlag) {
                    return false;
                }
            }

            return andFlag;
        });
    }
}
