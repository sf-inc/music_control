package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.categories.MusicCategories;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.WorldEventHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldEventHandler.class)
public class WorldEventHandlerMixin {
    @WrapOperation(method = "playJukeboxSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;setRecordPlayingOverlay(Lnet/minecraft/text/Text;)V"))
    private void useRightRecordName(InGameHud instance, Text description, Operation<Void> original,
                                    @Local(ordinal = 0) SoundInstance soundInstance) {
        Identifier soundId = soundInstance.getSound().getIdentifier();
        original.call(instance, Text.translatable(soundId.toString()));

        if (MusicControlClient.currentCategory.equals(Music.ALL_MUSICS)
                || MusicControlClient.currentCategory.equals(Music.ALL_MUSIC_DISCS)) {
            MusicCategories.PLAYED_MUSICS.add(soundId);
        }
    }
}
