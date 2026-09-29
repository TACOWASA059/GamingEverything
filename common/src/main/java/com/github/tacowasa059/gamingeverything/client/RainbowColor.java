package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.awt.Color;

public final class RainbowColor {
    private RainbowColor() {}

    public static int rgb(float phase) {
        return rgb(phase, GamingConfig.INSTANCE.speed);
    }

    public static int rgb(float phase, float speed) {
        // The UI value is a multiplier, not raw cycles per second. Keep CPU-drawn
        // colors and shader-drawn colors on exactly the same calibrated clock.
        float hue = (float) ((System.nanoTime() / 1_000_000_000.0D
                * GamingConfig.animationRate(speed) + phase) % 1.0D);
        return Color.HSBtoRGB(hue, 0.9F, 1.0F) & 0xFFFFFF;
    }

    public static float channel(float phase, int channel) {
        return channel(phase, channel, GamingConfig.INSTANCE.speed);
    }

    public static float channel(float phase, int channel, float speed) {
        int color = rgb(phase, speed);
        return switch (channel) {
            case 0 -> ((color >> 16) & 255) / 255.0F;
            case 1 -> ((color >> 8) & 255) / 255.0F;
            default -> (color & 255) / 255.0F;
        };
    }

    public static float tint(float original, float phase, int channel) {
        return Mth.lerp(GamingConfig.INSTANCE.intensity, original, channel(phase, channel));
    }

    public static float tint(float original, float phase, int channel, float strength, float speed) {
        return Mth.lerp(strength, original, channel(phase, channel, speed));
    }

    public static float tint(float original, float phase, int channel, float strength) {
        return Mth.lerp(strength, original, channel(phase, channel));
    }

    public static Vector3f vector(float phase) {
        int color = rgb(phase);
        return vector(color);
    }

    public static Vector3f vector(float phase, float speed) {
        return vector(rgb(phase, speed));
    }

    private static Vector3f vector(int color) {
        return new Vector3f(((color >> 16) & 255) / 255.0F,
                ((color >> 8) & 255) / 255.0F, (color & 255) / 255.0F);
    }
}
