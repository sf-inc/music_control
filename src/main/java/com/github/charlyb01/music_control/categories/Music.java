package com.github.charlyb01.music_control.categories;

import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Music implements Comparable<Music> {
    public final static String ALL_MUSICS = "all";
    public final static String ALL_MUSIC_DISCS = "disc";
    public final static String DEFAULT_MUSICS = "default";

    public final static Identifier EMPTY_MUSIC_ID = SoundManager.MISSING_SOUND.getIdentifier();
    public final static String EMPTY_MUSIC = EMPTY_MUSIC_ID.toString();

    public final static HashMap<String, ArrayList<Music>> MUSIC_BY_NAMESPACE = new HashMap<>();
    public final static ArrayList<Identifier> EVENTS = new ArrayList<>();
    public final static ArrayList<Identifier> BLACK_LISTED_EVENTS = new ArrayList<>(List.of(new Identifier("music.overworld.old_growth_taiga")));
    public final static HashMap<Identifier, HashSet<Music>> MUSIC_BY_EVENT = new HashMap<>();
    public final static HashMap<Identifier, HashSet<Identifier>> EVENTS_OF_EVENT = new HashMap<>();
    public final static HashMap<Identifier, Text> TRANSLATION_CACHE = new HashMap<>();

    private static Language LAST_LANG_INSTANCE = Language.getInstance();

    private final Identifier identifier;
    private final HashSet<Identifier> events;

    public Music(final Identifier identifier) {
        this.identifier = identifier;
        this.events = new HashSet<>();
    }

    public static Music getMusicFromIdentifier(final Identifier identifier) {
        int index = MUSIC_BY_NAMESPACE.get(ALL_MUSICS).indexOf(new Music(identifier));
        return index < 0 ? null : MUSIC_BY_NAMESPACE.get(ALL_MUSICS).get(index);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public HashSet<Identifier> getEvents() {
        return events;
    }

    public void addEvent(final Identifier event) {
        if (MUSIC_BY_EVENT.containsKey(event)) {
            MUSIC_BY_EVENT.get(event).add(this);
            this.events.add(event);
        }
    }

    public void removeEvent(final Identifier event) {
        if (MUSIC_BY_EVENT.containsKey(event)) {
            MUSIC_BY_EVENT.get(event).remove(this);
            this.events.remove(event);
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

    public static Text getTranslatedText(Identifier identifier) {
        if (LAST_LANG_INSTANCE != Language.getInstance()) {
            TRANSLATION_CACHE.clear();
            LAST_LANG_INSTANCE = Language.getInstance();
        }

        if (TRANSLATION_CACHE.containsKey(identifier)) {
            return TRANSLATION_CACHE.get(identifier);
        }

        final String idString = identifier.toString();
        final String path = identifier.getPath();
        if (LAST_LANG_INSTANCE.hasTranslation(idString)) {
            TRANSLATION_CACHE.put(identifier, Text.translatable(idString));

        // Get official biome translation for biomes' music
        // And format it
        } else if (MusicIdentifier.isBiome(identifier)) {
            TRANSLATION_CACHE.put(identifier, Text.translatable(
                    "music.format.biome", Text.translatable(
                            "biome.minecraft." + path.split("\\.", 3)[2])));
        // Format disc
        } else if (MusicIdentifier.isDisc(identifier)) {
            TRANSLATION_CACHE.put(identifier, Text.translatable(
                    "music.format.disc", Text.translatable(path)));
        }

        return TRANSLATION_CACHE.getOrDefault(identifier, Text.translatable(idString));
    }
}
