package com.github.charlyb01.music_control.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Redirect(method = "playSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;setRecordPlayingOverlay(Lnet/minecraft/text/Text;)V"))
    private void removeOldPrint(InGameHud instance, Text description) {
        // Don't call print because chosen song is unknown yet
    }
}
