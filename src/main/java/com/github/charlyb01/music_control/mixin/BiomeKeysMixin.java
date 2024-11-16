package com.github.charlyb01.music_control.mixin;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.charlyb01.music_control.client.SoundEventRegistry.NAME_BIOME_MAP;

@Mixin(BiomeKeys.class)
public class BiomeKeysMixin {
    @Inject(method = "keyOf", at = @At("RETURN"))
    private static void getVanillaBiomeNames(String name, CallbackInfoReturnable<RegistryKey<Biome>> cir) {
        RegistryKey<Biome> biomeRegistryKey = cir.getReturnValue();
        NAME_BIOME_MAP.put(biomeRegistryKey.getValue(), biomeRegistryKey);
    }
}
