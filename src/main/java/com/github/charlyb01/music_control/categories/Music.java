package com.github.charlyb01.music_control.categories;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;

public class Music {
    public final static ArrayList<Music> MUSICS = new ArrayList<>();
    public final static ArrayList<Music> DISCS = new ArrayList<>();
    public final static ArrayList<Music> CUSTOMS = new ArrayList<>();

    private final Identifier identifier;
    private Dimension dimension;
    private final HashSet<Event> events;
    private boolean isDisc;

    public Identifier getIdentifier() {
        return identifier;
    }

    public Music(final Identifier identifier) {
        this.identifier = identifier;
        this.events = new HashSet<>();
        this.isDisc = false;
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void addEvent(final Event event) {
        events.add(event);
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(final Dimension dimension) {
        this.dimension = dimension;
        this.dimension.addMusic(this);
    }

    public boolean isDisc() {
        return isDisc;
    }

    public void setDisc() {
        isDisc = true;
        DISCS.add(this);
    }
}
