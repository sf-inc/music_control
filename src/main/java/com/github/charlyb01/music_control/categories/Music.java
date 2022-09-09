package com.github.charlyb01.music_control.categories;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;

public class Music {
    public final static ArrayList<Music> MUSICS = new ArrayList<>();
    public final static ArrayList<Music> DISCS = new ArrayList<>();
    public final static ArrayList<Music> CUSTOMS = new ArrayList<>();

    private final Identifier identifier;
    private final HashSet<Dimension> dimensions;

    public Identifier getIdentifier() {
        return identifier;
    }

    public Music(final Identifier identifier, final boolean isDisc) {
        this.identifier = identifier;
        this.dimensions = new HashSet<>();
        if (isDisc) {
            DISCS.add(this);
        }
    }

    public HashSet<Dimension> getDimensions() {
        return dimensions;
    }

    public void addDimension(final Dimension dimension) {
        dimension.addMusic(this);
        this.dimensions.add(dimension);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Music && this.identifier.equals(((Music) obj).identifier);
    }
}
