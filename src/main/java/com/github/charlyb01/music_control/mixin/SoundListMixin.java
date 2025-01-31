package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.categories.Music;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Map;

@Mixin(SoundManager.SoundList.class)
public class SoundListMixin {
    @Shadow @Final
    Map<Identifier, WeightedSoundSet> loadedSounds;

    @Inject(method = "register", at = @At("TAIL"))
    private void addEverySound(Identifier id, SoundEntry entry, CallbackInfo ci, @Local(ordinal = 0) ResourceFactory resourceFactory) {
        if (entry.canReplace()) {
            Music.EVENTS_OF_EVENT.remove(id);
        }
        for (Sound sound : entry.getSounds()) {
            final Identifier identifier = sound.getIdentifier();
            if (!identifier.getPath().contains("music") && !identifier.getPath().contains("records")) continue;

            switch (sound.getRegistrationType()) {
                case FILE -> {
                    if (SoundManager.isSoundResourcePresent(sound, id, resourceFactory)) {
                        WeightedSoundSet newWeightedSoundSet = new WeightedSoundSet(id, entry.getSubtitle());

                        this.loadedSounds.put(identifier, newWeightedSoundSet);
                        newWeightedSoundSet.add(sound);
                    }
                }
                case SOUND_EVENT -> {
                    if (!Music.EVENTS_OF_EVENT.containsKey(id)) {
                        Music.EVENTS_OF_EVENT.put(id, new HashSet<>());
                    }
                    Music.EVENTS_OF_EVENT.get(id).add(identifier);
                }
            }
        }
    }
}
