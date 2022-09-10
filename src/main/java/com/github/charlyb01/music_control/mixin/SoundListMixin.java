package com.github.charlyb01.music_control.mixin;

import net.minecraft.client.sound.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(targets = "net/minecraft/client/sound/SoundManager$SoundList")
public class SoundListMixin {
    @Shadow @Final
    Map<Identifier, WeightedSoundSet> loadedSounds;

    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/client/sound/SoundEntry;Lnet/minecraft/resource/ResourceManager;)V", at = @At("TAIL"))
    private void addEverySound(Identifier id, SoundEntry entry, ResourceManager resourceManager, CallbackInfo ci) {

        for (Sound sound : entry.getSounds()) {
            final Identifier identifier = sound.getIdentifier();

            if (sound.getRegistrationType() == Sound.RegistrationType.FILE
                    && SoundManager.isSoundResourcePresent(sound, id, resourceManager)
                    && (identifier.getPath().contains("music") || identifier.getPath().contains("records"))) {
                WeightedSoundSet weightedSoundSet = new WeightedSoundSet(id, entry.getSubtitle());

                this.loadedSounds.put(identifier, weightedSoundSet);
                weightedSoundSet.add(sound);
            }
        }
    }
}
