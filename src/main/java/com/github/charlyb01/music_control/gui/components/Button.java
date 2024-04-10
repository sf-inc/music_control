package com.github.charlyb01.music_control.gui.components;

import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.config.ScrollSpeed;
import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.Text;

public class Button extends WButton {
    private String text = null;
    private boolean shouldUpdate = false;
    private int offset = 0;
    private int tickCount = 0;
    private int length = 0;
    private final int maxLength;
    private final static String BLANK_SPACE = "     ";

    public Button(int width) {
        this.maxLength = width / 7;
    }

    public void setText(String text) {
        String truncatedText = text.substring(0, Math.min(text.length(), this.maxLength));
        this.text = text + BLANK_SPACE + truncatedText;
        this.length = text.length() + BLANK_SPACE.length();
        this.shouldUpdate = text.length() > this.maxLength;
        this.setLabel(Text.of(truncatedText));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.shouldUpdate || ModConfig.get().cosmetics.gui.scrollSpeed.equals(ScrollSpeed.DISABLED)) {
            return;
        }

        if (!this.isHovered() || this.offset == this.length) {
            if (this.offset != 0) {
                this.offset = 0;
                this.tickCount = 0;
                this.setLabel(Text.of(this.text.substring(0, this.maxLength)));
            }
            return;
        }

        if (++this.tickCount < ModConfig.get().cosmetics.gui.scrollSpeed.delay
                || this.tickCount % ModConfig.get().cosmetics.gui.scrollSpeed.tick != 0) {
            return;
        }

        ++this.offset;
        this.setLabel(Text.of(this.text.substring(this.offset, this.maxLength + this.offset)));
    }
}
