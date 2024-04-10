package com.github.charlyb01.music_control.gui.components;

import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TextFilter extends WBox {
    public static int HEIGHT = 20;

    private static final Text CLEAR_TEXT = Text.of("×");
    private static final Text PLACEHOLDER_TEXT = Text.translatable("gui.component.filter.placeholder");

    private final Consumer<String> onChange;
    private final TextField textField;

    public void runOnChange() {
        var task = new Runnable() {
            @Override
            public void run() {
                onChange.accept(textField.getText());
            }
        };
        task.run();
    }

    public TextFilter(
        final Consumer<String> onChange,
        final int width
    ) {
        super(Axis.HORIZONTAL);
        this.onChange = onChange;

        ArrayList<Text> tooltips = new ArrayList<>(List.of(Text.translatable("gui.component.filter.tooltip"),
                Text.translatable("gui.component.filter.tooltip1")));
        this.textField = new TextField(PLACEHOLDER_TEXT, tooltips);
        this.textField.setChangedListener((s) -> runOnChange());

        WButton clearButton = new WButton(CLEAR_TEXT);
        clearButton.setOnClick(() -> this.textField.setText(""));

        this.add(this.textField, width - clearButton.getWidth() - 6, HEIGHT);
        this.add(clearButton, clearButton.getWidth(), HEIGHT);
    }
}
