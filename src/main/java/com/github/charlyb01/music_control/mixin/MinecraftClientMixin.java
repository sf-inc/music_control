package com.github.charlyb01.music_control.mixin;

import com.github.charlyb01.music_control.ResourcePackUtils;
import com.github.charlyb01.music_control.client.SoundEventBiome;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow private volatile boolean paused;

    @Shadow @Final private MusicTracker musicTracker;

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;tick(Z)V"))
    private void musicTrackerAlwaysTick(CallbackInfo ci) {
        if (ModConfig.get().musicDontPause && this.paused) {
            this.musicTracker.tick();
        }
    }

    @Inject(method = "getMusicType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAbilities()Lnet/minecraft/entity/player/PlayerAbilities;"), cancellable = true)
    private void cancelCreativeBeforeBiome(CallbackInfoReturnable<MusicSound> cir) {
        if (ModConfig.get().creativeFallback) {
            cir.setReturnValue(getMusicFromMap());
        }
    }

    @Inject(method = "getMusicType", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getMusic()Ljava/util/Optional;"), cancellable = true)
    private void useMapInsteadOfOptionalMusic(CallbackInfoReturnable<MusicSound> cir) {
        cir.setReturnValue(getMusicFromMap());
    }

    @Unique
    private MusicSound getMusicFromMap() {
        RegistryEntry<Biome> registryEntry = this.player.getWorld().getBiome(this.player.getBlockPos());
        RegistryKey<Biome> registryKey = registryEntry.getKey().orElse(null);
        if (ResourcePackUtils.isEnabled()
                && registryKey != null
                && SoundEventBiome.BIOME_MUSIC_MAP.containsKey(registryKey)) {
            return MusicType.createIngameMusic(RegistryEntry.of(SoundEventBiome.BIOME_MUSIC_MAP.get(registryKey)));
        }

        return registryEntry.value().getMusic().orElse(MusicType.GAME);
    }
}
