package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class GamingCommands {
    private GamingCommands() {}

    public static int open() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> minecraft.gui.setScreen(new GamingSettingsScreen(null)));
        return 1;
    }

    public static int set(String category, boolean enabled) {
        GamingConfig config = GamingConfig.INSTANCE;
        switch (category) {
            case "master" -> config.enabled = enabled;
            case "blocks" -> config.worldWash = enabled;
            case "sky" -> config.rainbowSky = enabled;
            case "entities" -> config.entities = enabled;
            case "hands" -> config.hands = enabled;
            case "items" -> config.items = enabled;
            case "gui" -> config.guiEffects = enabled;
            case "particles" -> config.particles = enabled;
            case "hud" -> config.hudFrame = enabled;
            case "special" -> config.specialEffects = enabled;
            default -> { return 0; }
        }
        GamingConfig.save();
        message(category, enabled);
        return 1;
    }

    public static int toggle(String category) {
        return set(category, !get(category));
    }

    private static boolean get(String category) {
        GamingConfig config = GamingConfig.INSTANCE;
        return switch (category) {
            case "master" -> config.enabled;
            case "blocks" -> config.worldWash;
            case "sky" -> config.rainbowSky;
            case "entities" -> config.entities;
            case "hands" -> config.hands;
            case "items" -> config.items;
            case "gui" -> config.guiEffects;
            case "particles" -> config.particles;
            case "hud" -> config.hudFrame;
            case "special" -> config.specialEffects;
            default -> false;
        };
    }

    private static void message(String category, boolean enabled) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.sendSystemMessage(Component.translatable(
                    "command.gamingeverything.changed",
                    Component.translatable("command.gamingeverything.category." + category),
                    Component.translatable(enabled
                            ? "screen.gamingeverything.on" : "screen.gamingeverything.off")));
        }
    }
}
