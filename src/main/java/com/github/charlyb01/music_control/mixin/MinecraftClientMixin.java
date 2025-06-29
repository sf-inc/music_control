package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.SoundEventRegistry;
import com.github.charlyb01.music_control.config.DimensionEventChance;
import com.github.charlyb01.music_control.config.ModConfig;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.MusicType;
import net.minecraft.util.collection.Pool;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(value = MinecraftClient.class, priority = 100)
public class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;

    @ModifyExpressionValue(method = "getMusicInstance", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/MusicType;END:Lnet/minecraft/sound/MusicSound;"))
    private MusicSound updateEndMusic(MusicSound original) {
        return ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.FALLBACK)
                ? getMusicFromMap(original)
                : original;
    }

    @ModifyExpressionValue(method = "getMusicInstance", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/MusicType;CREATIVE:Lnet/minecraft/sound/MusicSound;"))
    private MusicSound updateCreativeMusic(MusicSound original) {
        return ModConfig.get().general.event.creativeEventFallback
                ? getMusicFromMap(original)
                : original;
    }

    @Definition(id = "getMusic", method = "Lnet/minecraft/world/biome/Biome;getMusic()Ljava/util/Optional;")
    @Expression("?.getMusic()")
    @ModifyExpressionValue(method = "getMusicInstance", at = @At("MIXINEXTRAS:EXPRESSION"))
    private Optional<Pool<MusicSound>> updateBiomeMusic(Optional<Pool<MusicSound>> original) {
        RegistryEntry<Biome> registryEntry = this.player.getWorld().getBiome(this.player.getBlockPos());
        RegistryKey<Biome> registryKey = registryEntry.getKey().orElse(null);
        if (registryKey == null || !SoundEventRegistry.BIOME_MUSIC_MAP.containsKey(registryKey)) return original;

        MusicSound musicSound = MusicType.createIngameMusic(RegistryEntry.of(SoundEventRegistry.BIOME_MUSIC_MAP.get(registryKey)));
        return Optional.of(Pool.of(musicSound));
    }

    @ModifyExpressionValue(method = "getMusicInstance", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/MusicType;GAME:Lnet/minecraft/sound/MusicSound;"))
    private MusicSound updateGameMusic(MusicSound original) {
        return getMusicFromMap(original);
    }

    @Unique
    private MusicSound getMusicFromMap(final MusicSound original) {
        RegistryEntry<Biome> registryEntry = this.player.getWorld().getBiome(this.player.getBlockPos());
        RegistryKey<Biome> registryKey = registryEntry.getKey().orElse(null);
        if (registryKey == null || !SoundEventRegistry.BIOME_MUSIC_MAP.containsKey(registryKey)) return original;

        return MusicType.createIngameMusic(RegistryEntry.of(SoundEventRegistry.BIOME_MUSIC_MAP.get(registryKey)));
    }
}
