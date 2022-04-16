package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
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
        Identifier identifier;
        int i, size = MusicControlClient.currentCategory.musics.size();

        while (MusicCategories.PLAYED_MUSICS.size() >= Math.min(ModConfig.get().musicQueue, size))
            MusicCategories.PLAYED_MUSICS.poll();

        do {
            i = random.nextInt(size);
            identifier = this.get(i);
        } while (MusicCategories.PLAYED_MUSICS.contains(identifier) && size > MusicCategories.PLAYED_MUSICS.size());

        MusicCategories.PLAYED_MUSICS.add(identifier);

        return identifier;
    }

    public void add(final Identifier identifier) {
        this.musics.add(identifier);
    }

    public void clear() {
        this.musics.clear();
    }

    public boolean isEmpty() {
        return this.musics.isEmpty();
    }
}
