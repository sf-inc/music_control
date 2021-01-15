package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.imixin.ISoundSetMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MusicCategories {
    public final static Map<Identifier, Integer> GAME = new HashMap<>();
    public final static Map<Identifier, Integer> NETHER = new HashMap<>();
    public final static Map<Identifier, Integer> END = new HashMap<>();
    public final static Map<Identifier, Integer> DISC = new HashMap<>();
    public final static Map<Identifier, Integer> ALL = new HashMap<>();

    private MusicCategories() {}

    public static void init (final MinecraftClient client) {
        MusicControlClient.init = true;

        for (Identifier id : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(id) != null) {
                if (id.toString().startsWith("minecraft:music.game")
                        || id.toString().startsWith("minecraft:music.creative")
                        || id.toString().startsWith("minecraft:music.menu")
                        || id.toString().startsWith("minecraft:music.under_water")) {

                    GAME.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());
                    ALL.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());

                } else if (id.toString().startsWith("minecraft:music.nether")) {

                    int size = 1;
                    if (NETHER.isEmpty()) {
                        size = ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize();
                    }
                    NETHER.put(id, size);
                    ALL.put(id, size);

                } else if (id.toString().startsWith("minecraft:music.end")
                        || id.toString().startsWith("minecraft:music.dragon")
                        || id.toString().startsWith("minecraft:music.credits")) {

                    END.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());
                    ALL.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());

                } else if (id.toString().startsWith("minecraft:music_disc")) {

                    DISC.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());
                    ALL.put(id, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(id)))).getSoundsSize());
                }
            }
        }
    }

    public static void changeCategory(final Random random) {
        MusicCategory musicCategory;
        int i;
        do {
            i = MathHelper.nextInt(random, 0, MusicCategory.values().length - 1);
            musicCategory = MusicCategory.values()[i];

        } while (musicCategory.equals(MusicControlClient.currentCategory)
                || musicCategory.equals(MusicCategory.ALL));

        MusicControlClient.currentCategory = musicCategory;
    }

    public static Identifier chooseIdentifier(final Random random) {
        Identifier identifier = null;
        int acc = 0;
        int i = MathHelper.nextInt(random, 0, getCategoryWeight(MusicControlClient.currentCategory) - 1);

        for (Map.Entry<Identifier, Integer> entry : MusicControlClient.currentCategory.musics.entrySet()) {
            acc += entry.getValue();
            if (i < acc) {
                identifier = entry.getKey();
                break;
            }
        }

        return identifier;
    }

    public static int getCategoryWeight (final MusicCategory musicCategory) {
        int weight = 0;
        for (Map.Entry<Identifier, Integer> entry : musicCategory.musics.entrySet()) {
            weight += entry.getValue();
        }
        return weight;
    }
}
