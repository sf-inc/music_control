package com.github.charlyb01.music_control.categories;

import net.minecraft.util.Identifier;

import java.util.Map;

public enum MusicCategory {
    GAME (MusicCategories.GAME.values().stream().reduce(0, Integer::sum),
            MusicCategories.GAME),
    NETHER (MusicCategories.NETHER.values().stream().reduce(0, Integer::sum),
            MusicCategories.NETHER),
    END (MusicCategories.END.values().stream().reduce(0, Integer::sum),
            MusicCategories.END),
    DISC (MusicCategories.DISC.values().stream().reduce(0, Integer::sum),
            MusicCategories.DISC),
    ALL (MusicCategories.ALL.values().stream().reduce(0, Integer::sum),
            MusicCategories.ALL);

    public final int weight;
    public final Map<Identifier, Integer> musics;

    MusicCategory(final int weight, final Map<Identifier, Integer> map) {
        this.weight = weight;
        this.musics = map;
    }
}
