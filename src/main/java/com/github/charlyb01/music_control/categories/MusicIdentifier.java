package com.github.charlyb01.music_control.categories;

import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.client.SoundEventRegistry;
import com.github.charlyb01.music_control.config.DimensionEventChance;
import com.github.charlyb01.music_control.config.MiscEventChance;
import com.github.charlyb01.music_control.config.ModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;

import static com.github.charlyb01.music_control.Utils.isNight;
import static com.github.charlyb01.music_control.categories.Music.*;

public class MusicIdentifier {
    private MusicIdentifier() {}

    public static HashSet<Music> getListFromEvent(final Identifier eventId, final PlayerEntity player,
                                                    final World world, final Random random) {
        HashSet<Music> musics = new HashSet<>();

        if (ModConfig.get().general.event.miscEventChance.equals(MiscEventChance.HALF)
                && random.nextBoolean()) {
            musics.addAll(getListFromEvent(eventId));
            addDimensionEvent(musics, world, random);
            return musics;
        }

        boolean playerNotNull = player != null;

        if (playerNotNull && player.isFallFlying()) musics.addAll(getListFromEvent(SoundEventRegistry.PLAYER_FLYING));
        if (playerNotNull && player.hasVehicle()) musics.addAll(getListFromEvent(SoundEventRegistry.PLAYER_RIDING));
        if (isNight(world)) musics.addAll(getListFromEvent(SoundEventRegistry.TIME_NIGHT));
        if (world.isRaining()) musics.addAll(getListFromEvent(SoundEventRegistry.WEATHER_RAIN));
        if (world.isThundering()) musics.addAll(getListFromEvent(SoundEventRegistry.WEATHER_THUNDER));

        if (!ModConfig.get().general.event.miscEventChance.equals(MiscEventChance.PROPORTIONAL)
                && !musics.isEmpty()) {
            return musics;
        }

        musics.addAll(getListFromEvent(eventId));
        addDimensionEvent(musics, world, random);
        return musics;
    }

    private static void addDimensionEvent(final HashSet<Music> musics, final World world, final Random random) {
        if (ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.FALLBACK)) return;
        if (ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.NEVER)) return;

        HashSet<Music> dimensionMusic = getListFromEvent(getDimension(world.getRegistryKey()));
        if (ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.HALF)) {
            if (random.nextBoolean()) {
                return;
            } else if (!dimensionMusic.isEmpty()){
                musics.clear();
            }
        }

        musics.addAll(dimensionMusic);
    }

    public static HashSet<Music> getListFromEvent(final Identifier eventId) {
        HashSet<Music> musics = new HashSet<>();
        HashSet<Identifier> checkedEvents = new HashSet<>();
        ArrayList<Identifier> eventsToCheck = new ArrayList<>();
        eventsToCheck.add(eventId);
        while (!eventsToCheck.isEmpty()) {
            Identifier event = eventsToCheck.removeFirst();
            if (checkedEvents.contains(event)) continue;

            musics.addAll(MUSIC_BY_EVENT.get(event));
            checkedEvents.add(event);
            if (EVENTS_OF_EVENT.containsKey(event)) {
                eventsToCheck.addAll(EVENTS_OF_EVENT.get(event));
            }
        }

        return musics;
    }

    public static Identifier getFromList(final HashSet<Music> musics, final Random random) {
        if (musics.isEmpty()) return null;

        Identifier music;
        int size = musics.size();

        while (MusicCategories.PLAYED_MUSICS.size() >= Math.min(ModConfig.get().general.misc.musicQueue, size)) {
            MusicCategories.PLAYED_MUSICS.poll();
        }

        ArrayList<Music> musicList = new ArrayList<>(musics);
        do {
            music = musicList.remove(random.nextInt(size--)).getIdentifier();
        } while (MusicCategories.PLAYED_MUSICS.contains(music));

        MusicCategories.PLAYED_MUSICS.add(music);
        return music;
    }

    public static Identifier getFromCategory(final Random random) {
        if (MUSIC_BY_NAMESPACE.containsKey(MusicControlClient.currentCategory)) {
            HashSet<Music> musics = MUSIC_BY_NAMESPACE.get(MusicControlClient.currentCategory);
            return getFromList(musics, random);
        } else {
            return null;
        }
    }

    private static Identifier getDimension(final RegistryKey<World> world) {
        Identifier eventId;
        if (world.equals(World.NETHER)) {
            eventId = Identifier.ofVanilla("music.nether");
        } else if (world.equals(World.END)) {
            eventId = SoundEvents.MUSIC_END.value().getId();
        } else {
            eventId = SoundEvents.MUSIC_GAME.value().getId();
        }
        return eventId;
    }

    public static Identifier getFallback(final RegistryKey<World> world, final boolean creative, final Random random) {
        HashSet<Music> musics = null;
        if (ModConfig.get().general.event.dimensionEventChance.equals(DimensionEventChance.FALLBACK)) {
            musics = getListFromEvent(getDimension(world));
        }

        if ((musics == null || musics.isEmpty())
                && ModConfig.get().general.event.creativeEventFallback
                && creative) {
            musics = getListFromEvent(SoundEvents.MUSIC_CREATIVE.value().getId());
        }

        return musics == null || musics.isEmpty()
                ? EMPTY_MUSIC_ID
                : MusicIdentifier.getFromList(musics, random);
    }

    public static boolean isDimension(final Identifier identifier) {
        String path = identifier.getPath();
        return path.contentEquals("music.game")
                || path.contentEquals("music.nether")
                || path.contentEquals("music.end");
    }

    public static boolean isBiome(final Identifier identifier) {
        String path = identifier.getPath();
        return path.startsWith("music.overworld.")
                || path.startsWith("music.nether.")
                || path.startsWith("music.end.");
    }

    public static boolean isDisc(final Identifier identifier) {
        return identifier.getPath().startsWith("music_disc");
    }

    public static boolean isMisc(final Identifier identifier) {
        return identifier.getPath().startsWith("music.misc.");
    }
}
