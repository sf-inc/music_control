package com.github.charlyb01.music_control.categories;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Dimension {
    public final static Dimension OVERWORLD = new Dimension(World.OVERWORLD);
    public final static Dimension NETHER = new Dimension(World.NETHER);
    public final static Dimension END = new Dimension(World.END);
    public final static ArrayList<Dimension> DIMENSIONS;

    static {
        DIMENSIONS = new ArrayList<>();
        DIMENSIONS.add(OVERWORLD);
        DIMENSIONS.add(NETHER);
        DIMENSIONS.add(END);
    }

    private final RegistryKey<World> worldRegistryKey;
    private final ArrayList<Music> musics;

    public Dimension(final RegistryKey<net.minecraft.world.World> worldRegistryKey) {
        this.worldRegistryKey = worldRegistryKey;
        this.musics = new ArrayList<>();
    }

    public RegistryKey<World> getWorldRegistryKey() {
        return worldRegistryKey;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void addMusic(final Music music) {
        musics.add(music);
    }
}
