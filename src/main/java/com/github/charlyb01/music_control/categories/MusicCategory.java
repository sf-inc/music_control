package com.github.charlyb01.music_control.categories;

public enum MusicCategory {
    DEFAULT,
    DIMENSION,
    DISC,
    CUSTOM,
    ALL

    /*private final HashSet<Identifier> musics = new HashSet<>();

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
    }*/
}
