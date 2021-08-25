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

import java.util.*;

public class MusicCategories {
    public final static List<Identifier> OVERWORLD = new ArrayList<>();
    public final static List<Identifier> NETHER = new ArrayList<>();
    public final static List<Identifier> END = new ArrayList<>();
    public final static List<Identifier> DISC = new ArrayList<>();
    public final static List<Identifier> CUSTOM = new ArrayList<>();
    public final static List<Identifier> ALL = new ArrayList<>();

    public final static Map<String, Integer> CUSTOM_LIST = new HashMap<>();

    private MusicCategories() {}

    public static void init(final MinecraftClient client) {
        if (MusicControlClient.init) {
            OVERWORLD.clear();
            NETHER.clear();
            END.clear();
            DISC.clear();
            CUSTOM.clear();
            CUSTOM_LIST.clear();
            ALL.clear();
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
                        if (id.contains("game")
                                || id.contains("creative")
                                || id.contains("menu")
                                || id.contains("under_water")) {

                            OVERWORLD.add(soundContainer.getSound().getIdentifier());
                            ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("nether")) {

                            NETHER.add(soundContainer.getSound().getIdentifier());
                            ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("end")
                                || id.contains("dragon")
                                || id.contains("credits")) {

                            END.add(soundContainer.getSound().getIdentifier());
                            ALL.add(soundContainer.getSound().getIdentifier());

                        } else if (id.contains("disc")) {

                            DISC.add(soundContainer.getSound().getIdentifier());
                            ALL.add(soundContainer.getSound().getIdentifier());

                        } else {
                            if (CUSTOM_LIST.get(namespace) == null) {
                                CUSTOM_LIST.put(namespace, 1);
                            } else {
                                CUSTOM_LIST.put(namespace, CUSTOM_LIST.get(namespace) + 1);
                            }

                            CUSTOM.add(soundContainer.getSound().getIdentifier());
                            ALL.add(soundContainer.getSound().getIdentifier());
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
            } else if (!CUSTOM.isEmpty()) {
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

    public static Identifier chooseIdentifier(final Random random) {
        int i = random.nextInt(MusicControlClient.currentCategory.musics.size());
        return MusicControlClient.currentCategory.musics.get(i);
    }
}
