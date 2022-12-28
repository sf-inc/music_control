package com.github.charlyb01.music_control.mixin;

import net.minecraft.client.sound.*;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(targets = "net/minecraft/client/sound/SoundManager$SoundList")
public class SoundListMixin {
    @Shadow @Final
    Map<Identifier, WeightedSoundSet> loadedSounds;

    @Inject(method = "register", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void addEverySound(Identifier id, SoundEntry entry, CallbackInfo ci, WeightedSoundSet weightedSoundSet, boolean bl, ResourceFactory resourceFactory) {

        for (Sound sound : entry.getSounds()) {
            final Identifier identifier = sound.getIdentifier();

            if (sound.getRegistrationType() == Sound.RegistrationType.FILE
                    && SoundManager.isSoundResourcePresent(sound, id, resourceFactory)
                    && (identifier.getPath().contains("music") || identifier.getPath().contains("records"))) {
                WeightedSoundSet newWeightedSoundSet = new WeightedSoundSet(id, entry.getSubtitle());

                this.loadedSounds.put(identifier, newWeightedSoundSet);
                newWeightedSoundSet.add(sound);
            }
        }
    }
}
