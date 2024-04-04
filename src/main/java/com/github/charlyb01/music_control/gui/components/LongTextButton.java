package com.github.charlyb01.music_control.gui.components;

import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.config.ScrollSpeed;
import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.Text;

public class LongTextButton extends WButton {
    private String text = null;
    private boolean shouldUpdate = false;
    private int offset = 0;
    private int tickCount = 0;
    private int length = 0;
    private final int maxLength;
    private final static String BLANK_SPACE = "     ";

    public LongTextButton(int width) {
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
        if (!this.shouldUpdate || ModConfig.get().scrollSpeed.equals(ScrollSpeed.DISABLED)
                || ++this.tickCount % ModConfig.get().scrollSpeed.tick != 0) {
            return;
        }

        if (!this.isHovered() && this.offset != 0) {
            this.offset = 0;
            this.setLabel(Text.of(this.text.substring(0, this.maxLength)));

        } else if (this.isHovered()) {
            this.offset = (this.offset + 1) % this.length;
            this.setLabel(Text.of(this.text.substring(this.offset, this.maxLength + this.offset)));
        }
    }
}
