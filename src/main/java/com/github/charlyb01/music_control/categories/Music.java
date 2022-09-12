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
    public final static HashMap<String, ArrayList<Music>> MUSIC_LISTS = new HashMap<>();
    public final static ArrayList<String> EVENTS = new ArrayList<>();

    private final Identifier identifier;
    private final HashSet<String> events;

    public Identifier getIdentifier() {
        return identifier;
    }

    public Music(final Identifier identifier) {
        this.identifier = identifier;
        this.events = new HashSet<>();
    }

    public HashSet<String> getEvents() {
        return events;
    }

    public void addEvent(final String event) {
        if (!event.equals(identifier.getPath())) {
            this.events.add(event);
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
