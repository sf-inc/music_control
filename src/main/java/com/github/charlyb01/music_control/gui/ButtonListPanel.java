package com.github.charlyb01.music_control.gui;

import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.BiConsumer;

public class ButtonListPanel extends WBox {
    public ButtonListPanel(final List<Identifier> data, final BiConsumer<Identifier, WButton> onClicked,
                           final int width, final int height) {
        super(Axis.VERTICAL);

        BiConsumer<Identifier, WButton> configurator = (Identifier identifier, WButton button) -> {
            button.setLabel(Text.of(Text.translatable(identifier.toString()).asTruncatedString(width / 7)));
            button.setOnClick(() -> onClicked.accept(identifier, button));
        };

        this.add(new WListPanel<>(data, WButton::new, configurator), width, height);
    }
}
