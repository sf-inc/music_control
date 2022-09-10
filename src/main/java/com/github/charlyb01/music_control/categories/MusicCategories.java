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
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.github.charlyb01.music_control.categories.Dimension.DIMENSIONS;
import static com.github.charlyb01.music_control.categories.Music.MUSICS;
import static com.github.charlyb01.music_control.categories.Music.CUSTOMS;
import static com.github.charlyb01.music_control.categories.Music.DISCS;


public class MusicCategories {
    public final static LinkedList<Music> PLAYED_MUSICS = new LinkedList<>();
    public final static HashMap<String, Integer> CUSTOM_LIST = new HashMap<>();

    private MusicCategories() {}

    public static void init(final MinecraftClient client) {
        if (MusicControlClient.init) {
            MUSICS.clear();
            PLAYED_MUSICS.clear();
            CUSTOM_LIST.clear();
        } else {
            MusicControlClient.init = true;
        }

        Random random = Random.createLocal();

        for (Identifier identifier : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(identifier) != null) {

                List<SoundContainer<Sound>> sounds = ((SoundSetAccessor) client.getSoundManager().get(identifier)).getSounds();
                String namespace = identifier.getNamespace();
                String path = identifier.getPath();

                if (!path.contains("music"))
                    continue;

                for (SoundContainer<Sound> soundContainer : sounds) {

                    Identifier musicIdentifier = soundContainer.getSound(random).getIdentifier();
                    Music music = new Music(musicIdentifier, path.contains("music_disc"));
                    if (MUSICS.contains(music)) {
                        music = MUSICS.get(MUSICS.indexOf(music));
                        music.addEvent(identifier);
                        continue;
                    }

                    MUSICS.add(music);
                    music.addEvent(identifier);

                    if (namespace.equals("minecraft")) {

                        if (path.contains("nether")) {

                            music.addDimension(Dimension.NETHER);

                        } else if (path.contains("end")
                                || path.contains("dragon")
                                || path.contains("credits")) {

                            music.addDimension(Dimension.END);

                        } else {
                            music.addDimension(Dimension.OVERWORLD);
                        }
                    } else {
                        CUSTOM_LIST.merge(namespace, 1, Integer::sum);
                        CUSTOMS.add(music);
                    }
                }
            }
        }

        if (!CUSTOM_LIST.isEmpty()) {
            MusicControlClient.currentSubCategory = (String) CUSTOM_LIST.keySet().toArray()[0];
        } else if (MusicControlClient.currentCategory.equals(MusicCategory.CUSTOM)) {
            MusicControlClient.currentCategory = MusicCategory.DIMENSION;
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
                updateDimension(player.clientWorld);
                MusicControlClient.currentCategory = MusicCategory.DIMENSION;
            } else if (!Music.CUSTOMS.isEmpty()) {
                MusicControlClient.currentCategory = MusicCategory.CUSTOM;
            } else {
                MusicControlClient.currentCategory = MusicCategory.DEFAULT;
            }
        }
    }

    public static void updateDimension(final ClientWorld world) {
        if (MusicControlClient.init && world != null) {
            for (Dimension dimension : DIMENSIONS) {
                if (world.getRegistryKey().equals(dimension.getWorldRegistryKey()))
                    MusicControlClient.currentDimension = dimension;
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

    private static Identifier getRandomMusicIdentifier(final ArrayList<Music> musics, final Random random) {
        Music music;
        int size = musics.size();

        while (MusicCategories.PLAYED_MUSICS.size() >= Math.min(ModConfig.get().musicQueue, size))
            MusicCategories.PLAYED_MUSICS.poll();

        do {
            music = musics.get(random.nextInt(size));
        } while (MusicCategories.PLAYED_MUSICS.contains(music) && size > MusicCategories.PLAYED_MUSICS.size());

        MusicCategories.PLAYED_MUSICS.add(music);
        return music.getIdentifier();
    }

    public static Identifier getMusicIdentifier(final Random random) {
        ArrayList<Music> musics = null;
        switch (MusicControlClient.currentCategory) {
            case DIMENSION -> musics = MusicControlClient.currentDimension.getMusics();
            case DISC -> musics = DISCS;
            case CUSTOM, ALL -> musics = MUSICS; // Separate in the future
            case DEFAULT -> {
                return null;
            }
        }

        return getRandomMusicIdentifier(musics, random);
    }
}
