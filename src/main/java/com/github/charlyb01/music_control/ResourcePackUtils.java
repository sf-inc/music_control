package com.github.charlyb01.music_control;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static com.github.charlyb01.music_control.categories.Music.MUSIC_BY_EVENT;
import static com.github.charlyb01.music_control.categories.MusicCategories.NAMESPACES;

public class ResourcePackUtils {
    private ResourcePackUtils() {}

    protected static final String RESOURCEPACK_PROFILE_NAME = "file/" + MusicControlClient.MOD_ID;
    protected static Path RESOURCEPACK_PATH = null;
    protected static Path ASSETS_PATH = null;
    protected static boolean WAS_CREATED = false;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static boolean exists() {
        return MinecraftClient.getInstance().getResourcePackManager().getNames().stream().anyMatch(
                name -> name.startsWith(RESOURCEPACK_PROFILE_NAME));
    }

    public static boolean wasCreatedOrIsEnabled() {
        return WAS_CREATED || MinecraftClient.getInstance().getResourcePackManager().getEnabledNames().stream().anyMatch(
                name -> name.startsWith(RESOURCEPACK_PROFILE_NAME));
    }

    public static void writeConfig() {
        if (WAS_CREATED) {
            WAS_CREATED = false;
        } else if (RESOURCEPACK_PATH == null) {
            setPaths();
        }

        HashMap<String, FileWriter> fileWriters = new HashMap<>();
        HashMap<String, JsonObject> jsonObjects = new HashMap<>();
        for (String namespace : NAMESPACES) {
            jsonObjects.put(namespace, new JsonObject());
            try {
                fileWriters.put(namespace, new FileWriter(ResourcePackUtils.getSoundPath(namespace).toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MUSIC_BY_EVENT.forEach((Identifier event, HashSet<Music> musics) -> {
            JsonArray soundsJson = new JsonArray();
            for (Music music : musics) {
                JsonObject musicJson = new JsonObject();
                musicJson.addProperty("name", music.getIdentifier().toString());
                musicJson.addProperty("stream", true);

                soundsJson.add(musicJson);
            }

            JsonObject soundEventJson = new JsonObject();
            soundEventJson.addProperty("category", "music");
            soundEventJson.addProperty("replace", true);
            soundEventJson.add("sounds", soundsJson);

            jsonObjects.get(event.getNamespace()).add(event.getPath(), soundEventJson);
        });

        fileWriters.forEach((String namespace, FileWriter fileWriter) -> {
            try (PrintWriter out = new PrintWriter(fileWriter)) {
                out.write(GSON.toJson(jsonObjects.get(namespace)));
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void createResourcePack() {
        String resourcePackProfileName = findNextAvailablePath();

        try {
            Files.createDirectories(RESOURCEPACK_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        createMetaFile();
        createIcon();

        try {
            Files.createDirectories(ASSETS_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (String namespace : NAMESPACES) {
            final Path namespacePath = ASSETS_PATH.resolve(namespace);
            try {
                Files.createDirectories(namespacePath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        WAS_CREATED = true;
        MinecraftClient.getInstance().getResourcePackManager().scanPacks();
        MinecraftClient.getInstance().getResourcePackManager().enable(resourcePackProfileName);
    }

    private static String findNextAvailablePath() {
        String resourcePackName = MusicControlClient.MOD_ID;
        int i = 0;

        final Path resourcePackDir = MinecraftClient.getInstance().getResourcePackDir();
        RESOURCEPACK_PATH = resourcePackDir.resolve(resourcePackName);

        while (Files.exists(RESOURCEPACK_PATH)) {
            resourcePackName = MusicControlClient.MOD_ID + "_" + ++i;
            RESOURCEPACK_PATH = resourcePackDir.resolve(resourcePackName);
        }

        ASSETS_PATH = RESOURCEPACK_PATH.resolve("assets");
        return "file/" + resourcePackName;
    }

    private static void setPaths() {
        Optional<String> selectedResourcePack = MinecraftClient.getInstance().getResourcePackManager().getEnabledNames().stream()
                .filter(name -> name.startsWith(RESOURCEPACK_PROFILE_NAME)).findFirst();
        selectedResourcePack.ifPresent(name -> {
            RESOURCEPACK_PATH = MinecraftClient.getInstance().getResourcePackDir().resolve(name.substring(5));
            ASSETS_PATH = RESOURCEPACK_PATH.resolve("assets");
        });
    }

    private static void createMetaFile() {
        Path path = RESOURCEPACK_PATH.resolve("pack.mcmeta");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);

                JsonObject data = new JsonObject();
                JsonObject pack = new JsonObject();
                pack.addProperty("pack_format", SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES));
                pack.addProperty("description", Text.translatable("music_control.metadata.description").getString());
                data.add("pack", pack);

                try (PrintWriter out = new PrintWriter(new FileWriter(path.toFile()))) {
                    out.write(GSON.toJson(data));
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createIcon() {
        Path targetPath = RESOURCEPACK_PATH.resolve("pack.png");
        if (Files.exists(targetPath)) {
            return;
        }

        Path sourcePath;
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MusicControlClient.MOD_ID);
        if (modContainer.isPresent()) {
            Optional<String> iconPath = modContainer.get().getMetadata().getIconPath(400);
            if (iconPath.isPresent()) {
                sourcePath = Path.of(iconPath.get());
            } else {
                return;
            }
        } else {
            return;
        }

        if (!Files.exists(sourcePath)) {
            return;
        }

        try {
            Files.copy(sourcePath, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getSoundPath(final String namespace) {
        Path path = ASSETS_PATH.resolve(namespace).resolve("sounds.json");
        if (Files.exists(path)) {
            return path;
        }

        try {
            return Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
