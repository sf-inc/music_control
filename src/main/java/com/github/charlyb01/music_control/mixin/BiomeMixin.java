package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.access.BiomeAccess;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Biome.class)
public class BiomeMixin implements BiomeAccess {
    @Shadow @Final private BiomeEffects effects;

    @Override
    public void setMusic(final RegistryEntry.Reference<SoundEvent> soundEvent) {
        ((BiomeEffectsIMixin) this.effects).setMusic(Optional.of(MusicType.createIngameMusic(soundEvent)));
    }
}
