package com.github.charlyb01.music_control.categories;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Music implements Comparable<Music> {
    public final static String ALL_MUSICS = "all";
    public final static String ALL_MUSIC_DISCS = "disc";
    public final static String DEFAULT_MUSICS = "default";
    public final static HashMap<String, ArrayList<Music>> MUSIC_BY_NAMESPACE = new HashMap<>();
    public final static ArrayList<Identifier> EVENTS = new ArrayList<>();
    public final static HashMap<Identifier, HashSet<Music>> MUSIC_BY_EVENT = new HashMap<>();

    private final Identifier identifier;
    private final HashSet<Identifier> events;

    public Music(final Identifier identifier) {
        this.identifier = identifier;
        this.events = new HashSet<>();
    }

    public static Music getMusicFromIdentifier(final Identifier identifier) {
        int index = MUSIC_BY_NAMESPACE.get(ALL_MUSICS).indexOf(new Music(identifier));
        return index < 0 ? null : MUSIC_BY_NAMESPACE.get(ALL_MUSICS).get(index);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public HashSet<Identifier> getEvents() {
        return events;
    }

    public void addEvent(final Identifier event) {
        if (MUSIC_BY_EVENT.containsKey(event)) {
            MUSIC_BY_EVENT.get(event).add(this);
            this.events.add(event);
        }
    }

    public void removeEvent(final Identifier event) {
        if (MUSIC_BY_EVENT.containsKey(event)) {
            MUSIC_BY_EVENT.get(event).remove(this);
            this.events.remove(event);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Music && this.identifier.equals(((Music) obj).identifier);
    }

    @Override
    public int compareTo(@NotNull Music music) {
        return this.identifier.compareTo(music.identifier);
    }
}
