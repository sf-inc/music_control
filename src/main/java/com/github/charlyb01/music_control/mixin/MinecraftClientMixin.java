package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.client.SoundEventRegistry;
import com.github.charlyb01.music_control.config.DimensionEventChance;
import com.github.charlyb01.music_control.config.ModConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicInstance;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private volatile boolean paused;

    @Shadow @Final private MusicTracker musicTracker;

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;tick(Z)V"))
    private void musicTrackerAlwaysTick(CallbackInfo ci) {
        if (ModConfig.get().general.misc.musicDontPause && this.paused) {
            this.musicTracker.tick();
        }
    }

    // TODO: Stop using ordinals
    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 2))
    private MusicInstance useEndMusic(MusicInstance original) {
        return ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.FALLBACK)
                ? getMusicFromMap(original)
                : original;
    }

    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 4))
    private MusicInstance cancelCreativeBeforeBiome(MusicInstance original) {
        return ModConfig.get().general.event.creativeEventFallback
                ? getMusicFromMap(original)
                : original;
    }

    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 5))
    private MusicInstance useMapInsteadOfOptionalMusic(MusicInstance original) {
        return getMusicFromMap(original);
    }

    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 6))
    private MusicInstance useMapInsteadOfGameMusic(MusicInstance original) {
        return getMusicFromMap(original);
    }

    @Unique
    private MusicInstance getMusicFromMap(final MusicInstance original) {
        RegistryEntry<Biome> registryEntry = this.player.getWorld().getBiome(this.player.getBlockPos());
        RegistryKey<Biome> registryKey = registryEntry.getKey().orElse(null);
        if (registryKey == null || !SoundEventRegistry.BIOME_MUSIC_MAP.containsKey(registryKey)) return original;

        float volume = registryEntry.value().getMusicVolume();
        return new MusicInstance(
                MusicType.createIngameMusic(RegistryEntry.of(SoundEventRegistry.BIOME_MUSIC_MAP.get(registryKey))),
                volume);
    }
}
