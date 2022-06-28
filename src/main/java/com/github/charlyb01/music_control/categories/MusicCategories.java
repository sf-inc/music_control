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

import java.util.*;

import static com.github.charlyb01.music_control.categories.Dimension.DIMENSIONS;
import static com.github.charlyb01.music_control.categories.Event.EVENTS;
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
            DIMENSIONS.clear();
            EVENTS.clear();
            PLAYED_MUSICS.clear();
            CUSTOM_LIST.clear();
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

                if (id.contains("music"))
                    System.out.println(namespace + " : " + id);
                for (SoundContainer<Sound> soundContainer : sounds) {
                    if (id.contains("music"))
                        System.out.println("musics : " + soundContainer.getSound().getIdentifier());
                    Music music = new Music(soundContainer.getSound().getIdentifier());

                    if (id.contains("records/")) {

                        MUSICS.add(music);
                        music.setDisc();    // Liste de catégories autres ?

                    } else if (id.contains("music/")) {

                        MUSICS.add(music);

                        if (id.contains("/nether")) {

                            music.setDimension(Dimension.NETHER);

                        } else if (id.contains("/end")
                                || id.contains("/boss")
                                || id.contains("/credits")) {

                            music.setDimension(Dimension.END);

                        } else if (id.contains("/game")
                                || id.contains("/creative")
                                || id.contains("/menu")
                                || id.contains("/under_water")) {

                            music.setDimension(Dimension.OVERWORLD);

                        } else {
                            if (CUSTOM_LIST.get(namespace) == null) {
                                CUSTOM_LIST.put(namespace, 1);
                            } else {
                                CUSTOM_LIST.put(namespace, CUSTOM_LIST.get(namespace) + 1);
                            }

                            CUSTOMS.add(music);
                        }
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
        if (MusicControlClient.init
                && MusicControlClient.currentCategory.equals(MusicCategory.DIMENSION)
                && world != null) {

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
            case EVENT, CUSTOM, ALL -> musics = MUSICS; // Separate in the future
            case DEFAULT -> {
                return null;
            }
        }

        return getRandomMusicIdentifier(musics, random);
    }
}
