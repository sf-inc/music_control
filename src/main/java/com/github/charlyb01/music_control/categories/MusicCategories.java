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
    public final static Map<Identifier, Integer> CUSTOM = new HashMap<>();
    public final static Map<Identifier, Integer> MODS = new HashMap<>();
    public final static Map<Identifier, Integer> ALL = new HashMap<>();

    public final static Map<String, Integer> MODS_LIST = new HashMap<>();

    private MusicCategories() {}

    public static void init (final MinecraftClient client) {
        if (MusicControlClient.init) {
            GAME.clear();
            NETHER.clear();
            END.clear();
            DISC.clear();
            CUSTOM.clear();
            MODS.clear();
            MODS_LIST.clear();
            ALL.clear();
        } else {
            MusicControlClient.init = true;
        }

        for (Identifier identifier : client.getSoundManager().getKeys()) {
            if (client.getSoundManager().get(identifier) != null) {

                String namespace = "";
                String id = "";
                if (identifier.toString().split(":").length > 1) {
                    namespace = identifier.toString().split(":")[0];
                    id = identifier.toString().split(":")[1];
                }

                if (namespace.equals("minecraft")) {
                    if (id.startsWith("music.game")
                            || id.startsWith("music.creative")
                            || id.startsWith("music.menu")
                            || id.startsWith("music.under_water")) {

                        GAME.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                        ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());

                    } else if (id.startsWith("music.nether")) {

                        NETHER.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                        ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());

                    } else if (id.startsWith("music.end")
                            || id.startsWith("music.dragon")
                            || id.startsWith("music.credits")) {

                        END.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                        ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());

                    } else if (id.startsWith("music_disc")) {

                        DISC.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                        ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                    }

                } else if (namespace.contains("music")) {

                    System.out.println("size "+((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                    CUSTOM.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                    ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());

                } else {
                    if (id.contains("music")) {
                        MODS_LIST.put(namespace, MODS_LIST.get(namespace) + 1);

                        MODS.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                        ALL.put(identifier, ((ISoundSetMixin) (Objects.requireNonNull(client.getSoundManager().get(identifier)))).getSoundsSize());
                    }
                }
            }
        }
    }

    public static void changeCategory () {
        int current = 0;
        int next;
        for (MusicCategory category: MusicCategory.values()) {
            if (MusicControlClient.currentCategory.equals(category)) {
                break;
            }
            current++;
        }
        next = (current + 1) % MusicCategory.values().length;

        if (MusicCategory.values()[next].equals(MusicCategory.CUSTOM)) {
            if (CUSTOM.isEmpty()) {
                next = 0;
            }
        } else if (MusicCategory.values()[next].equals(MusicCategory.ALL)) {
            next = 0;
        }

        MusicControlClient.currentCategory = MusicCategory.values()[next];
    }

    public static Identifier chooseIdentifier (final Random random) {
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
