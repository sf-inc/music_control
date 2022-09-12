package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.mixin.SoundSetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static com.github.charlyb01.music_control.categories.Music.*;


public class MusicCategories {
    public final static ArrayList<String> CATEGORIES = new ArrayList<>(Arrays.asList(ALL_MUSICS, DEFAULT_MUSICS, ALL_MUSIC_DISCS));
    public final static LinkedList<Music> PLAYED_MUSICS = new LinkedList<>();

    private MusicCategories() {}

    public static void init(final MinecraftClient client) {
        if (MusicControlClient.init) {
            PLAYED_MUSICS.clear();
            MUSIC_LISTS.clear();
        } else {
            MusicControlClient.init = true;
        }

        Random random = Random.createLocal();
        ArrayList<Music> musics = new ArrayList<>();
        ArrayList<Music> discs = new ArrayList<>();
        MUSIC_LISTS.put(ALL_MUSICS, musics);
        MUSIC_LISTS.put(ALL_MUSIC_DISCS, discs);

        for (Identifier identifier : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(identifier) != null) {

                List<SoundContainer<Sound>> sounds = ((SoundSetAccessor) client.getSoundManager().get(identifier)).getSounds();
                String namespace = identifier.getNamespace();
                String path = identifier.getPath();

                if (!path.contains("music"))
                    continue;

                for (SoundContainer<Sound> soundContainer : sounds) {

                    Identifier musicIdentifier = soundContainer.getSound(random).getIdentifier();
                    Music music = new Music(musicIdentifier);

                    if (musics.contains(music)) {
                        music = musics.get(musics.indexOf(music));
                        music.addEvent(path);
                        continue;
                    }
                    if (path.contains("music_disc")) {
                        discs.add(music);
                    }

                    musics.add(music);
                    music.addEvent(path);

                    if (!namespace.equals("minecraft")) {
                        ArrayList<Music> customMusics = MUSIC_LISTS.computeIfAbsent(namespace, k -> new ArrayList<>());
                        if (!customMusics.contains(music)) {
                            customMusics.add(music);
                        }
                        if (!CATEGORIES.contains(namespace)) {
                            CATEGORIES.add(namespace);
                        }
                    }
                }
            }
        }

        MUSIC_LISTS.forEach((String namespace, ArrayList<Music> musicList) -> Collections.sort(musicList));

        for (SoundEvent soundEvent : Registry.SOUND_EVENT) {
            String event = soundEvent.getId().getPath();
            if (event.contains("music") && !EVENTS.contains(event)) {
                EVENTS.add(event);
            }
        }

        if (!CATEGORIES.contains(MusicControlClient.currentCategory)) {
            MusicControlClient.currentCategory = DEFAULT_MUSICS;
        }
    }

    public static void changeCategory(final boolean nextCategory) {
        int index = nextCategory
                ? (CATEGORIES.indexOf(MusicControlClient.currentCategory) + 1) % CATEGORIES.size()
                : (CATEGORIES.indexOf(MusicControlClient.currentCategory) - 1);
        if (index < 0) {
            index =  CATEGORIES.size() - 1;
        }

        MusicControlClient.currentCategory = CATEGORIES.get(index);
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
        if (MUSIC_LISTS.containsKey(MusicControlClient.currentCategory)) {
            ArrayList<Music> musics = MUSIC_LISTS.get(MusicControlClient.currentCategory);
            return getRandomMusicIdentifier(musics, random);
        } else {
            return null;
        }
    }
}
