package com.github.charlyb01.music_control.gui.components;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class TextFieldTooltip extends WTextField {
    private final static int RENDER_DELAY = 20;

    private int tickCount = 0;
    private final ArrayList<Text> tooltips;

    public TextFieldTooltip(Text suggestion, ArrayList<Text> tooltips) {
        super(suggestion);
        this.setMaxLength(100);
        this.tooltips = tooltips;
    }

    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        super.addTooltip(tooltip);
        for (Text text : this.tooltips) {
            tooltip.add(text);
        }
    }

    @Override
    public void renderTooltip(DrawContext context, int x, int y, int tX, int tY) {
        if (this.tickCount < RENDER_DELAY) return;
        super.renderTooltip(context, x, y, tX, tY);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isHovered()) ++this.tickCount;
        else if (this.tickCount != 0) this.tickCount = 0;
    }
}
