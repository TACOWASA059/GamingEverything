package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.gui.GuiGraphics;

public final class GamingGuiRenderer {
    private GamingGuiRenderer() {}

    public static void renderScreenFrame(GuiGraphics graphics) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.guiEffects) return;

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        int alpha = Math.round(150.0F + 90.0F * config.intensity);
        horizontal(graphics, 0, width, 0, 2, alpha, 0.00F);
        horizontal(graphics, 0, width, height - 2, height, alpha, 0.50F);
        vertical(graphics, 0, 2, 2, height - 2, alpha, 0.25F);
        vertical(graphics, width - 2, width, 2, height - 2, alpha, 0.75F);
    }

    public static void renderWidgetFrame(GuiGraphics graphics, int x, int y, int width, int height,
                                         boolean highlighted, boolean active) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.guiEffects || width < 8 || height < 8) return;

        int base = highlighted ? 235 : 145;
        int alpha = Math.round(base * (0.45F + config.intensity * 0.55F));
        if (!active) alpha = Math.round(alpha * 0.55F);

        horizontal(graphics, x, x + width, y, y + 1, alpha, 0.00F);
        horizontal(graphics, x, x + width, y + height - 1, y + height, alpha, 0.50F);
        vertical(graphics, x, x + 1, y + 1, y + height - 1, alpha, 0.25F);
        vertical(graphics, x + width - 1, x + width, y + 1, y + height - 1, alpha, 0.75F);

        if (highlighted && width > 12 && height > 10) {
            int glowAlpha = Math.round(34.0F * config.intensity);
            horizontal(graphics, x + 1, x + width - 1, y + 1, y + 3, glowAlpha, 0.08F);
        }
    }

    public static void renderContainerFrame(GuiGraphics graphics, int x, int y, int width, int height) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.guiEffects || width < 16 || height < 16) return;

        int alpha = Math.round(185.0F + 60.0F * config.intensity);
        horizontal(graphics, x - 2, x + width + 2, y - 2, y, alpha, 0.00F);
        horizontal(graphics, x - 2, x + width + 2, y + height, y + height + 2, alpha, 0.50F);
        vertical(graphics, x - 2, x, y, y + height, alpha, 0.25F);
        vertical(graphics, x + width, x + width + 2, y, y + height, alpha, 0.75F);
        int glow = Math.round(28.0F * config.intensity);
        horizontal(graphics, x, x + width, y, y + 3, glow, 0.05F);
        horizontal(graphics, x, x + width, y + height - 3, y + height, glow, 0.55F);
    }

    public static void renderSlotFrame(GuiGraphics graphics, int x, int y, int size) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.guiEffects || size < 8) return;
        int alpha = Math.round(115.0F + 85.0F * config.intensity);
        horizontal(graphics, x, x + size, y, y + 1, alpha, 0.00F);
        horizontal(graphics, x, x + size, y + size - 1, y + size, alpha, 0.50F);
        vertical(graphics, x, x + 1, y + 1, y + size - 1, alpha, 0.25F);
        vertical(graphics, x + size - 1, x + size, y + 1, y + size - 1, alpha, 0.75F);
    }

    private static void horizontal(GuiGraphics graphics, int x0, int x1, int y0, int y1,
                                   int alpha, float phaseOffset) {
        int length = Math.max(1, x1 - x0);
        int segments = Math.max(4, Math.min(32, length / 8));
        for (int i = 0; i < segments; i++) {
            int start = x0 + length * i / segments;
            int end = x0 + length * (i + 1) / segments;
            graphics.fill(start, y0, Math.max(start + 1, end), y1,
                    argb(alpha, guiColor(phaseOffset + i / (float) segments)));
        }
    }

    private static void vertical(GuiGraphics graphics, int x0, int x1, int y0, int y1,
                                 int alpha, float phaseOffset) {
        int length = Math.max(1, y1 - y0);
        int segments = Math.max(4, Math.min(24, length / 7));
        for (int i = 0; i < segments; i++) {
            int start = y0 + length * i / segments;
            int end = y0 + length * (i + 1) / segments;
            graphics.fill(x0, start, x1, Math.max(start + 1, end),
                    argb(alpha, guiColor(phaseOffset + i / (float) segments)));
        }
    }

    private static int argb(int alpha, int rgb) {
        return (Math.max(0, Math.min(255, alpha)) << 24) | (rgb & 0xFFFFFF);
    }

    private static int guiColor(float phase) {
        GamingConfig config = GamingConfig.INSTANCE;
        return RainbowColor.rgb(phase / config.guiWavelength, config.guiSpeed);
    }
}
