package com.github.charlyb01.music_control.gui.components;

import java.util.function.Consumer;

import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

public class TextFilter extends WBox {

    private static final Text DEFAULT_CLEAR_TEXT = Text.of("×");
    private static final Text DEFAULT_PLACEHOLDER = Text.translatable("gui.component.filter.placeholder");

    private final Consumer<String> onChange;
    private final WTextField textField;
    private final WButton clearButton;

    private void runOnChange() {
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
        final int width, 
        final int height
    ) {
        super(Axis.HORIZONTAL);
        this.onChange = onChange;

        this.textField = new WTextField(DEFAULT_PLACEHOLDER);
        this.textField.setEditable(true);
        this.textField.setText("");
        this.textField.setChangedListener((s) -> runOnChange());
        
        this.clearButton = new WButton(DEFAULT_CLEAR_TEXT);
        this.clearButton.setOnClick(() -> this.textField.setText(""));

        this.add(textField);
        this.add(clearButton);

        this.setSize(width > -1 ? width : getWidth(), height > -1 ? height : getHeight());
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        this.textField.setSize(w - this.clearButton.getWidth() - 6, h);
        this.clearButton.setSize(this.clearButton.getWidth(), h);
    }
}
