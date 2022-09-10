package com.github.charlyb01.music_control.categories;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;

public class Music {
    public final static ArrayList<Music> MUSICS = new ArrayList<>();
    public final static ArrayList<Music> DISCS = new ArrayList<>();
    public final static ArrayList<Music> CUSTOMS = new ArrayList<>();
    public final static ArrayList<Identifier> EVENTS = new ArrayList<>();

    private final Identifier identifier;
    private final HashSet<Identifier> events;

    public Identifier getIdentifier() {
        return identifier;
    }

    public Music(final Identifier identifier, final boolean isDisc) {
        this.identifier = identifier;
        this.events = new HashSet<>();
        if (isDisc) {
            DISCS.add(this);
        }
    }

    public HashSet<Identifier> getEvents() {
        return events;
    }

    public void addEvent(final Identifier event) {
        if (!EVENTS.contains(event)) {
            EVENTS.add(event);
        }
        this.events.add(event);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Music && this.identifier.equals(((Music) obj).identifier);
    }
}
