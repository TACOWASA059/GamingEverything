package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;

public final class GamingClient {
    private static KeyMapping settingsKey;

    private GamingClient() {}

    public static void initialize(KeyMapping keyMapping) {
        settingsKey = keyMapping;
        GamingConfig.load();
    }

    public static void tick(Minecraft minecraft) {
        if (settingsKey != null) {
            while (settingsKey.consumeClick()) {
                minecraft.setScreen(new GamingSettingsScreen(minecraft.screen));
            }
        }
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.particles || minecraft.level == null || minecraft.player == null
                || (minecraft.player.tickCount & 1) != 0) {
            return;
        }
        double angle = minecraft.player.tickCount * 0.28D;
        double radius = 0.75D + minecraft.level.random.nextDouble() * 0.65D;
        minecraft.level.addParticle(new DustParticleOptions(RainbowColor.vector((float) (angle / 30.0D)), 0.8F),
                minecraft.player.getX() + Math.cos(angle) * radius,
                minecraft.player.getY() + 0.15D + minecraft.level.random.nextDouble() * 1.7D,
                minecraft.player.getZ() + Math.sin(angle) * radius,
                0.0D, 0.012D, 0.0D);
    }
}

