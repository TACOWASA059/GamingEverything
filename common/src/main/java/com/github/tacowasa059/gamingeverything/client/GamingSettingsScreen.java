package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class GamingSettingsScreen extends Screen {
    private final Screen parent;

    public GamingSettingsScreen(Screen parent) {
        super(Component.translatable("screen.gamingeverything.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int left = width / 2 - 155;
        int right = width / 2 + 5;
        int y = height / 2 - 104;
        GamingConfig c = GamingConfig.INSTANCE;
        addToggle(left, y, "screen.gamingeverything.enabled", () -> c.enabled, v -> c.enabled = v);
        addToggle(right, y, "screen.gamingeverything.entities", () -> c.entities, v -> c.entities = v);
        y += 24;
        addToggle(left, y, "screen.gamingeverything.hands", () -> c.hands, v -> c.hands = v);
        addToggle(right, y, "screen.gamingeverything.items", () -> c.items, v -> c.items = v);
        y += 24;
        addToggle(left, y, "screen.gamingeverything.sky", () -> c.rainbowSky, v -> c.rainbowSky = v);
        addToggle(right, y, "screen.gamingeverything.particles", () -> c.particles, v -> c.particles = v);
        y += 24;
        addToggle(left, y, "screen.gamingeverything.wash", () -> c.worldWash, v -> c.worldWash = v);
        addToggle(right, y, "screen.gamingeverything.gui", () -> c.guiEffects, v -> c.guiEffects = v);
        y += 24;
        addToggle(left, y, "screen.gamingeverything.hud", () -> c.hudFrame, v -> c.hudFrame = v);
        addToggle(right, y, "screen.gamingeverything.special", () -> c.specialEffects, v -> c.specialEffects = v);
        y += 28;
        addRenderableWidget(Button.builder(Component.translatable("screen.gamingeverything.animation"),
                        b -> minecraft.setScreen(new GamingAnimationSettingsScreen(this)))
                .bounds(left, y, 310, 20).build());
        y += 28;
        addRenderableWidget(new IntensitySlider(left, y, 310, 20));
        y += 28;
        addRenderableWidget(Button.builder(Component.translatable("screen.gamingeverything.done"), b -> onClose())
                .bounds(width / 2 - 75, y, 150, 20).build());
    }

    private void addToggle(int x, int y, String key, BooleanSupplier getter, Consumer<Boolean> setter) {
        addRenderableWidget(Button.builder(toggleLabel(key, getter.getAsBoolean()), button -> {
            boolean value = !getter.getAsBoolean();
            setter.accept(value);
            button.setMessage(toggleLabel(key, value));
            GamingConfig.save();
        }).bounds(x, y, 150, 20).build());
    }

    private static Component toggleLabel(String key, boolean value) {
        return Component.translatable(key).append(": ").append(
                Component.translatable(value ? "screen.gamingeverything.on" : "screen.gamingeverything.off"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(font, title, width / 2, height / 2 - 130,
                RainbowColor.rgb(0.0F, GamingConfig.INSTANCE.guiSpeed));
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        GamingConfig.save();
        minecraft.setScreen(parent);
    }

    private static final class IntensitySlider extends net.minecraft.client.gui.components.AbstractSliderButton {
        private IntensitySlider(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty(), GamingConfig.INSTANCE.intensity);
            updateMessage();
        }

        @Override protected void updateMessage() {
            setMessage(Component.translatable("screen.gamingeverything.intensity",
                    Math.round(GamingConfig.INSTANCE.intensity * 100.0F)));
        }

        @Override protected void applyValue() {
            GamingConfig.INSTANCE.intensity = (float) value;
            GamingConfig.save();
        }
    }
}
