package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Random;

public enum MusicCategory {
    DEFAULT,
    OVERWORLD,
    NETHER,
    END,
    DISC,
    CUSTOM,
    ALL;

    private final HashSet<Identifier> musics = new HashSet<>();

    public Identifier get(final int i) {
        return (Identifier) this.musics.toArray()[i];
    }

    public Identifier get(final Random random) {
        int i, size = MusicControlClient.currentCategory.musics.size();
        do {
            i = random.nextInt(size);
        } while (this.get(i).equals(MusicControlClient.currentMusic) && size > 1);

        return this.get(i);
    }

    public void add(final Identifier identifier){
        this.musics.add(identifier);
    }

    public void clear() {
        this.musics.clear();
    }

    public boolean isEmpty() {
        return this.musics.isEmpty();
    }
}
