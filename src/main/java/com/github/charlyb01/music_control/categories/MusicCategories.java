package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.mixin.SoundSetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MusicCategories {
    public final static HashMap<String, Integer> CUSTOM_LIST = new HashMap<>();

    private MusicCategories() {}

    public static void init(final MinecraftClient client) {
        if (MusicControlClient.init) {
            CUSTOM_LIST.clear();
            for (MusicCategory musicCategory : MusicCategory.values()) {
                musicCategory.clear();
            }
        } else {
            MusicControlClient.init = true;
        }

        for (Identifier identifier : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(identifier) != null) {

                List<SoundContainer<Sound>> sounds = ((SoundSetAccessor) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSounds();
                String namespace = "";
                String id = "";
                if (identifier.toString().split(":").length > 1) {
                    namespace = identifier.toString().split(":")[0];
                    id = identifier.toString().split(":")[1];
                }

                for (SoundContainer<Sound> soundContainer : sounds) {
                    if (id.contains("music")) {
                        if (id.contains("nether")) {

                            MusicCategory.NETHER.add(soundContainer.getSound().getIdentifier());
                            MusicCategory.ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("end")
                                || id.contains("boss")
                                || id.contains("credits")) {

                            MusicCategory.END.add(soundContainer.getSound().getIdentifier());
                            MusicCategory.ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("disc")) {

                            MusicCategory.DISC.add(soundContainer.getSound().getIdentifier());
                            MusicCategory.ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("game")
                                || id.contains("creative")
                                || id.contains("menu")
                                || id.contains("under_water")) {

                            MusicCategory.OVERWORLD.add(soundContainer.getSound().getIdentifier());
                            MusicCategory.ALL.add(soundContainer.getSound().getIdentifier());

                        } else {
                            if (identifier.equals(new Identifier("minecraft", "music.dragon"))) continue;

                            if (CUSTOM_LIST.get(namespace) == null) {
                                CUSTOM_LIST.put(namespace, 1);
                            } else {
                                CUSTOM_LIST.put(namespace, CUSTOM_LIST.get(namespace) + 1);
                            }

                            MusicCategory.CUSTOM.add(soundContainer.getSound().getIdentifier());
                            MusicCategory.ALL.add(soundContainer.getSound().getIdentifier());
                        }
                    }
                }
            }
        }

        if (!CUSTOM_LIST.isEmpty()) {
            MusicControlClient.currentSubCategory = (String) CUSTOM_LIST.keySet().toArray()[0];
        } else if (MusicControlClient.currentCategory.equals(MusicCategory.CUSTOM)) {
            MusicControlClient.currentCategory = MusicCategory.OVERWORLD;
        }
    }

    public static void changeCategory(final ClientPlayerEntity player) {
        boolean canChangeCategory = (player != null && player.isCreative()) || ModConfig.get().cheat;
        int current = 0;
        for (MusicCategory category : MusicCategory.values()) {
            if (MusicControlClient.currentCategory.equals(category)) {
                break;
            }
            current++;
        }

        if (canChangeCategory) {
            int next = (current + 1) % MusicCategory.values().length;

            if (MusicCategory.values()[current].equals(MusicCategory.CUSTOM)) {
                if (changeSubCategory()) {
                    next = current;
                } else {
                    next = 0;
                }
            }

            if (MusicCategory.values()[next].equals(MusicCategory.CUSTOM)) {
                if (CUSTOM_LIST.isEmpty()) {
                    next = 0;
                }
            } else if (MusicCategory.values()[next].equals(MusicCategory.ALL)) {
                next = 0;
            }

            MusicControlClient.currentCategory = MusicCategory.values()[next];
        } else {
            if (MusicControlClient.currentCategory.equals(MusicCategory.CUSTOM)) {
                MusicControlClient.currentCategory = MusicCategory.DEFAULT;
            } else if (MusicControlClient.currentCategory.equals(MusicCategory.DEFAULT) && player != null) {
                updateCategory(player.clientWorld);
            } else if (!MusicCategory.CUSTOM.isEmpty()) {
                MusicControlClient.currentCategory = MusicCategory.CUSTOM;
            } else {
                MusicControlClient.currentCategory = MusicCategory.DEFAULT;
            }
        }
    }

    public static void updateCategory(final ClientWorld world) {
        if (MusicControlClient.init && world != null) {
            if (world.getRegistryKey().equals(World.OVERWORLD)) {
                MusicControlClient.currentCategory = MusicCategory.OVERWORLD;

            } else if (world.getRegistryKey().equals(World.NETHER)) {
                MusicControlClient.currentCategory = MusicCategory.NETHER;

            } else if (world.getRegistryKey().equals(World.END)) {
                MusicControlClient.currentCategory = MusicCategory.END;
            }
        }
    }

    private static boolean changeSubCategory() {
        int current = 0;
        for (String subCategory: CUSTOM_LIST.keySet()) {
            if (MusicControlClient.currentSubCategory.equals(subCategory)) {
                break;
            }
            current++;
        }

        if (current < CUSTOM_LIST.keySet().size()-1) {
            MusicControlClient.currentSubCategory = (String) CUSTOM_LIST.keySet().toArray()[current+1];
            return true;
        } else {
            MusicControlClient.currentSubCategory = (String) CUSTOM_LIST.keySet().toArray()[0];
            return false;
        }
    }
}
