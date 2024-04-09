package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.config.FilterOperator;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.components.LongTextButton;
import com.github.charlyb01.music_control.gui.components.TextFilter;
import com.github.charlyb01.music_control.gui.components.WFilterListPanel;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ButtonListPanel extends WBox {
    private final WFilterListPanel<Identifier, LongTextButton> items;
    private final TextFilter filter;

    public ButtonListPanel(
            final List<Identifier> data,
            final BiConsumer<Identifier, LongTextButton> onClicked,
            final int width,
            final int height,
            final boolean withFilter) {
        super(Axis.VERTICAL);

        BiConsumer<Identifier, LongTextButton> configurator = (Identifier identifier, LongTextButton button) -> {
            button.setText(Music.getTranslatedText(identifier).getString());
            button.setOnClick(() -> onClicked.accept(identifier, button));
        };
        this.items = new WFilterListPanel<>(data, () -> new LongTextButton(width), configurator);

        if (withFilter && height > TextFilter.HEIGHT) {
            this.filter = new TextFilter(this::runFilter, width);
            this.add(this.filter, width, TextFilter.HEIGHT);
            this.add(this.items, width, height - TextFilter.HEIGHT);
            this.layout();
        } else {
            this.filter = null;
            this.add(this.items, width, height);
        }
    }

    public void update() {
        if (this.filter != null) {
            this.filter.runOnChange();
        }
    }

    private void runFilter(String s) {
        if (s == null || s.isEmpty()) {
            this.items.runFilter(null);
            return;
        }

        boolean operatorModifier = false;
        String[] wordsRaw = s.split(" ");
        ArrayList<String> words = new ArrayList<>();
        for (String word : wordsRaw) {
            if (word.isEmpty()) continue;

            if (word.charAt(0) == '&') {
                operatorModifier = true;
                if (word.length() > 1) {
                    words.add(word.substring(1));
                }
            } else {
                words.add(word);
            }
        }

        final boolean andOperator = ModConfig.get().cosmetics.gui.filterOperator.equals(FilterOperator.AND) ^ operatorModifier;
        this.items.runFilter((id) -> {
            for (String word : words) {
                if (andOperator && !match(word, id)) return false;
                if (!andOperator && match(word, id)) return true;
            }

            return andOperator;
        });
    }

    private boolean match(String word, Identifier id) {
        boolean inverted = word.charAt(0) == '!';
        if (inverted && word.length() < 2) return false;

        int firstCharIndex = inverted ? 1 : 0;
        char firstChar = word.charAt(firstCharIndex);

        boolean doesMatch;
        if (firstChar == '@') {
            doesMatch = id.getNamespace().contains(word.substring(firstCharIndex + 1));
        } else if (firstChar == '#') {
            doesMatch = id.getPath().contains(word.substring(firstCharIndex + 1));
        } else {
            String evaluated = Music.getTranslatedText(id).getString();
            doesMatch = firstChar == '$'
                    ? evaluated.contains(word.substring(firstCharIndex + 1))
                    : evaluated.toLowerCase().contains(word.substring(firstCharIndex).toLowerCase());
        }

        return doesMatch ^ inverted;
    }
}
