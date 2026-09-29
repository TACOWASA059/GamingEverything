package com.github.tacowasa059.gamingeverything.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GamingConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final float ANIMATION_RATE = 0.18F;
    public static GamingConfig INSTANCE = new GamingConfig();

    public int configVersion = 7;
    public boolean enabled = true;
    public boolean entities = true;
    public boolean hands = true;
    public boolean items = true;
    public boolean rainbowSky = true;
    public boolean particles = true;
    public boolean worldWash = true;
    public boolean hudFrame = true;
    public boolean guiEffects = true;
    public boolean specialEffects = true;
    public float speed = 6.0F;
    public float intensity = 0.62F;
    public float blockSpeed = 5.0F;
    public float blockWavelength = 1.0F;
    public float skySpeed = 3.0F;
    public float entitySpeed = 6.0F;
    public float entityWavelength = 1.0F;
    public float handSpeed = 6.0F;
    public float handWavelength = 1.0F;
    public float itemSpeed = 8.0F;
    public float itemWavelength = 1.0F;
    public float guiSpeed = 3.0F;
    public float guiWavelength = 1.0F;

    private static Path path() {
        return Minecraft.getInstance().gameDirectory.toPath().resolve("config/gamingeverything.json");
    }

    public static void load() {
        Path path = path();
        if (!Files.isRegularFile(path)) {
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            GamingConfig loaded = GSON.fromJson(json, GamingConfig.class);
            if (loaded != null) {
                // Missing configVersion means this is one of the original config files.
                // Field initializers otherwise make Gson report the current version and
                // silently skip every migration.
                int oldVersion = json.has("configVersion") ? json.get("configVersion").getAsInt() : 0;
                boolean migrated = oldVersion < 7;
                if (oldVersion < 2) {
                    loaded.speed = Math.max(0.65F, loaded.speed * 3.0F);
                    loaded.intensity = Math.max(0.62F, loaded.intensity);
                }
                if (oldVersion < 3) {
                    loaded.speed = Math.max(5.0F, loaded.speed * 5.0F);
                }
                if (oldVersion < 4) {
                    loaded.speed = Math.max(12.0F, loaded.speed * 2.4F);
                }
                if (oldVersion < 5) {
                    loaded.hands = true;
                    loaded.guiEffects = true;
                    loaded.specialEffects = true;
                    loaded.blockSpeed = loaded.speed;
                    loaded.skySpeed = loaded.speed;
                    loaded.entitySpeed = loaded.speed;
                    loaded.handSpeed = loaded.speed;
                    loaded.itemSpeed = loaded.speed;
                    loaded.guiSpeed = loaded.speed;
                    loaded.blockWavelength = 1.0F;
                    loaded.entityWavelength = 1.0F;
                    loaded.handWavelength = 1.0F;
                    loaded.itemWavelength = 1.0F;
                    loaded.guiWavelength = 1.0F;
                }
                if (oldVersion < 6) {
                    loaded.configVersion = 6;
                    loaded.speed = 6.0F;
                    loaded.blockSpeed = 10.0F;
                    loaded.skySpeed = 3.0F;
                    loaded.entitySpeed = 6.0F;
                    loaded.handSpeed = 6.0F;
                    loaded.itemSpeed = 8.0F;
                    loaded.guiSpeed = 3.0F;
                }
                if (oldVersion < 7) {
                    // Version 6 made only the block layer unnaturally fast.
                    // Preserve deliberate custom values, but migrate its shipped default.
                    if (Math.abs(loaded.blockSpeed - 10.0F) < 0.001F) {
                        loaded.blockSpeed = 5.0F;
                    }
                    loaded.configVersion = 7;
                }
                loaded.speed = clampSpeed(loaded.speed);
                loaded.blockSpeed = clampSpeed(loaded.blockSpeed);
                loaded.skySpeed = clampSpeed(loaded.skySpeed);
                loaded.entitySpeed = clampSpeed(loaded.entitySpeed);
                loaded.handSpeed = clampSpeed(loaded.handSpeed);
                loaded.itemSpeed = clampSpeed(loaded.itemSpeed);
                loaded.guiSpeed = clampSpeed(loaded.guiSpeed);
                loaded.blockWavelength = clampWavelength(loaded.blockWavelength);
                loaded.entityWavelength = clampWavelength(loaded.entityWavelength);
                loaded.handWavelength = clampWavelength(loaded.handWavelength);
                loaded.itemWavelength = clampWavelength(loaded.itemWavelength);
                loaded.guiWavelength = clampWavelength(loaded.guiWavelength);
                loaded.intensity = Math.max(0.0F, Math.min(1.0F, loaded.intensity));
                INSTANCE = loaded;
                if (migrated) save();
            }
        } catch (IOException | RuntimeException ignored) {
        }
    }

    public static float clampSpeed(float value) {
        return Math.max(0.25F, Math.min(20.0F, value));
    }

    public static float clampWavelength(float value) {
        return Math.max(0.25F, Math.min(4.0F, value));
    }

    public static float animationRate(float speed) {
        return speed * ANIMATION_RATE;
    }

    public static void save() {
        try {
            Files.createDirectories(path().getParent());
            try (Writer writer = Files.newBufferedWriter(path())) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
