package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;

public final class GamingOverlay {
    private GamingOverlay() {}

    public static void render(GuiGraphicsExtractor graphics) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled) return;

        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        int segments = Math.max(24, Math.min(72, width / 10));
        int sw = (width + segments - 1) / segments;
        if (!config.hudFrame) return;
        for (int i = 0; i < segments; i++) {
            int x0 = i * sw;
            int x1 = Math.min(width, x0 + sw + 1);
            int color = 0xE0000000 | hudColor(i / (float) segments);
            graphics.fill(x0, 0, x1, 2, color);
            graphics.fill(width - x1, height - 2, width - x0, height, color);
        }
        int vs = Math.max(12, segments * height / Math.max(1, width));
        int sh = (height + vs - 1) / vs;
        for (int i = 0; i < vs; i++) {
            int y0 = i * sh;
            int y1 = Math.min(height, y0 + sh + 1);
            int color = 0xE0000000 | hudColor(i / (float) vs + 0.25F);
            graphics.fill(0, y0, 2, y1, color);
            graphics.fill(width - 2, height - y1, width, height - y0, color);
        }
        int cx = width / 2;
        int cy = height / 2;
        graphics.fill(cx - 1, cy - 6, cx + 1, cy - 2, 0xD0000000 | hudColor(0.0F));
        graphics.fill(cx - 1, cy + 3, cx + 1, cy + 7, 0xD0000000 | hudColor(0.5F));
        graphics.fill(cx - 6, cy - 1, cx - 2, cy + 1, 0xD0000000 | hudColor(0.25F));
        graphics.fill(cx + 3, cy - 1, cx + 7, cy + 1, 0xD0000000 | hudColor(0.75F));

        if (config.guiEffects) {
            int hotbarX = cx - 91;
            int hotbarY = height - 22;
            GamingGuiRenderer.renderContainerFrame(graphics, hotbarX, hotbarY, 182, 22);
            for (int slot = 0; slot < 9; slot++) {
                GamingGuiRenderer.renderSlotFrame(graphics, hotbarX + 3 + slot * 20, hotbarY + 3, 16);
            }
        }
    }

    private static int hudColor(float phase) {
        GamingConfig config = GamingConfig.INSTANCE;
        return RainbowColor.rgb(phase / config.guiWavelength, config.guiSpeed);
    }
}
