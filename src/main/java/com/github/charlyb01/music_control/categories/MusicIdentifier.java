package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.HashSet;

import static com.github.charlyb01.music_control.categories.Music.*;

public class MusicIdentifier {
    private MusicIdentifier() {}

    public static ArrayList<Music> getListFromEvent(final Identifier eventId) {
        ArrayList<Music> musics = new ArrayList<>();
        HashSet<Identifier> checkedEvents = new HashSet<>();
        ArrayList<Identifier> eventsToCheck = new ArrayList<>();
        eventsToCheck.add(eventId);
        while (!eventsToCheck.isEmpty()) {
            Identifier event = eventsToCheck.remove(0);
            if (checkedEvents.contains(event)) continue;

            musics.addAll(MUSIC_BY_EVENT.get(event));
            checkedEvents.add(event);
            if (EVENTS_OF_EVENT.containsKey(event)) {
                eventsToCheck.addAll(EVENTS_OF_EVENT.get(event));
            }
        }

        return musics;
    }

    public static Identifier getFromList(final ArrayList<Music> musics, final Random random) {
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

    public static Identifier getFromCategory(final Random random) {
        if (MUSIC_BY_NAMESPACE.containsKey(MusicControlClient.currentCategory)) {
            ArrayList<Music> musics = MUSIC_BY_NAMESPACE.get(MusicControlClient.currentCategory);
            return getFromList(musics, random);
        } else {
            return null;
        }
    }
}
