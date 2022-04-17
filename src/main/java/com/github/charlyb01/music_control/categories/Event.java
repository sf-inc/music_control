package com.github.charlyb01.music_control.categories;

import java.util.HashSet;

public class Event {
    public final static HashSet<Event> EVENTS;

    static {
        EVENTS = new HashSet<>();
    }

    private final String event;
    private final HashSet<Music> musics;

    public Event(final String event) {
        this.event = event;
        this.musics = new HashSet<>();
    }

    void addMusic(final Music music) {
        musics.add(music);
    }
}
