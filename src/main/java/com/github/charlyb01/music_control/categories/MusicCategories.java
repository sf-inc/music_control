package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.client.SoundEventBiome;
import com.github.charlyb01.music_control.mixin.SoundSetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;

import java.util.*;

import static com.github.charlyb01.music_control.categories.Music.*;


public class MusicCategories {
    public final static ArrayList<String> CATEGORIES = new ArrayList<>(Arrays.asList(ALL_MUSICS, DEFAULT_MUSICS, ALL_MUSIC_DISCS));
    public final static ArrayList<String> NAMESPACES = new ArrayList<>(List.of("minecraft"));
    public final static LinkedList<Identifier> PLAYED_MUSICS = new LinkedList<>();

    private MusicCategories() {}

    public static void init(final MinecraftClient client) {
        if (MusicControlClient.init) {
            PLAYED_MUSICS.clear();
            MUSIC_BY_NAMESPACE.clear();
            MUSIC_BY_EVENT.clear();
            EVENTS.clear();

            SoundEventBiome.BIOME_MUSIC_MAP.clear();
        } else {
            MusicControlClient.init = true;
        }

        Random random = Random.createLocal();
        ArrayList<Music> musics = new ArrayList<>();
        ArrayList<Music> discs = new ArrayList<>();
        MUSIC_BY_NAMESPACE.put(ALL_MUSICS, musics);
        MUSIC_BY_NAMESPACE.put(ALL_MUSIC_DISCS, discs);

        for (SoundEvent soundEvent : Registries.SOUND_EVENT) {
            Identifier event = soundEvent.getId();
            if (event.getPath().contains("music")) {
                if (!EVENTS.contains(event) && !BLACK_LISTED_EVENTS.contains(event)) {
                    EVENTS.add(event);
                    MUSIC_BY_EVENT.put(event, new HashSet<>());
                }

                String[] split = event.getPath().split("\\.");
                RegistryKey<Biome> biomeRegistryKey;
                if (split.length > 0
                        && (biomeRegistryKey = SoundEventBiome.NAME_BIOME_MAP.get(
                                new Identifier(event.getNamespace(), split[split.length-1]))) != null) {
                    SoundEventBiome.BIOME_MUSIC_MAP.put(biomeRegistryKey, soundEvent);
                }
            }
        }

        for (Identifier eventIdentifier : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(eventIdentifier) != null) {

                List<SoundContainer<Sound>> sounds = ((SoundSetAccessor) client.getSoundManager().get(eventIdentifier)).getSounds();
                String namespace = eventIdentifier.getNamespace();
                String path = eventIdentifier.getPath();

                if (!path.contains("music"))
                    continue;

                for (SoundContainer<Sound> soundContainer : sounds) {

                    Identifier musicIdentifier = soundContainer.getSound(random).getIdentifier();
                    Music music = new Music(musicIdentifier);

                    if (musics.contains(music)) {
                        music = musics.get(musics.indexOf(music));
                        music.addEvent(eventIdentifier);
                        continue;
                    }
                    if (path.contains("music_disc")) {
                        discs.add(music);
                    }

                    musics.add(music);
                    music.addEvent(eventIdentifier);

                    if (!namespace.equals("minecraft")) {
                        ArrayList<Music> customMusics = MUSIC_BY_NAMESPACE.computeIfAbsent(namespace, k -> new ArrayList<>());
                        if (!customMusics.contains(music)) {
                            customMusics.add(music);
                        }
                        if (!CATEGORIES.contains(namespace)) {
                            CATEGORIES.add(namespace);
                            NAMESPACES.add(namespace);
                        }
                    }
                }
            }
        }

        MUSIC_BY_NAMESPACE.forEach((String namespace, ArrayList<Music> musicList) -> Collections.sort(musicList));
        Collections.sort(EVENTS);

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

    public static Identifier getRandomMusicIdentifier(final ArrayList<Music> musics, final Random random) {
        if (musics.isEmpty()) return null;

        Identifier music;
        int size = musics.size();

        while (MusicCategories.PLAYED_MUSICS.size() >= Math.min(ModConfig.get().general.misc.musicQueue, size)) {
            MusicCategories.PLAYED_MUSICS.poll();
        }

        do {
            music = musics.get(random.nextInt(size)).getIdentifier();
        } while (MusicCategories.PLAYED_MUSICS.contains(music) && size > MusicCategories.PLAYED_MUSICS.size());

        MusicCategories.PLAYED_MUSICS.add(music);
        return music;
    }

    public static Identifier getMusicIdentifier(final Random random) {
        if (MUSIC_BY_NAMESPACE.containsKey(MusicControlClient.currentCategory)) {
            ArrayList<Music> musics = MUSIC_BY_NAMESPACE.get(MusicControlClient.currentCategory);
            return getRandomMusicIdentifier(musics, random);
        } else {
            return null;
        }
    }
}
