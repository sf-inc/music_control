package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicSound.class)
public class MusicSoundMixin {
    @Inject(at = @At("HEAD"), method = "getMinDelay", cancellable = true)
    private void changeMinDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ModConfig.get().timer * 20);
    }

    @Inject(at = @At("HEAD"), method = "getMaxDelay", cancellable = true)
    private void changeMaxDelay(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ModConfig.get().timer * 20);
    }
}
