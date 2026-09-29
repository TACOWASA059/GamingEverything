package com.github.tacowasa059.gamingeverything.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class GamingAnimationSettingsScreen extends Screen {
    private final Screen parent;
    private final Category category;

    public GamingAnimationSettingsScreen(Screen parent) {
        this(parent, Category.BLOCKS);
    }

    private GamingAnimationSettingsScreen(Screen parent, Category category) {
        super(Component.translatable("screen.gamingeverything.animation"));
        this.parent = parent;
        this.category = category;
    }

    @Override
    protected void init() {
        int x = width / 2 - 155;
        int y = height / 2 - 68;
        addRenderableWidget(Button.builder(category.label(), b ->
                        minecraft.gui.setScreen(new GamingAnimationSettingsScreen(parent, category.next())))
                .bounds(x, y, 310, 20).build());
        y += 32;
        addRenderableWidget(new CategorySpeedSlider(x, y, 310, 20));
        y += 28;
        CategoryWavelengthSlider wavelength = new CategoryWavelengthSlider(x, y, 310, 20);
        wavelength.active = category.hasWavelength();
        addRenderableWidget(wavelength);
        y += 36;
        addRenderableWidget(Button.builder(Component.translatable("screen.gamingeverything.done"), b -> onClose())
                .bounds(width / 2 - 75, y, 150, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.centeredText(font, title, width / 2, height / 2 - 104,
                RainbowColor.rgb(0.0F, GamingConfig.INSTANCE.guiSpeed));
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        GamingConfig.save();
        minecraft.gui.setScreen(parent);
    }

    private final class CategorySpeedSlider extends AbstractSliderButton {
        private CategorySpeedSlider(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty(), (category.speed() - 0.25D) / 19.75D);
            updateMessage();
        }

        @Override protected void updateMessage() {
            setMessage(Component.translatable("screen.gamingeverything.category_speed",
                    String.format("%.2f", category.speed())));
        }

        @Override protected void applyValue() {
            category.speed((float) (0.25D + value * 19.75D));
            GamingConfig.save();
        }
    }

    private final class CategoryWavelengthSlider extends AbstractSliderButton {
        private CategoryWavelengthSlider(int x, int y, int width, int height) {
            super(x, y, width, height, Component.empty(),
                    (category.wavelength() - 0.25D) / 3.75D);
            updateMessage();
        }

        @Override protected void updateMessage() {
            if (!category.hasWavelength()) {
                setMessage(Component.translatable("screen.gamingeverything.no_wavelength"));
            } else {
                setMessage(Component.translatable("screen.gamingeverything.wavelength",
                        String.format("%.2f", category.wavelength())));
            }
        }

        @Override protected void applyValue() {
            if (category.hasWavelength()) {
                category.wavelength((float) (0.25D + value * 3.75D));
                GamingConfig.save();
            }
        }
    }

    private enum Category {
        BLOCKS("screen.gamingeverything.category.blocks"),
        SKY("screen.gamingeverything.category.sky"),
        ENTITIES("screen.gamingeverything.category.entities"),
        HANDS("screen.gamingeverything.category.hands"),
        ITEMS("screen.gamingeverything.category.items"),
        GUI("screen.gamingeverything.category.gui");

        private final String key;

        Category(String key) {
            this.key = key;
        }

        Component label() {
            return Component.translatable("screen.gamingeverything.category", Component.translatable(key));
        }

        Category next() {
            Category[] values = values();
            return values[(ordinal() + 1) % values.length];
        }

        boolean hasWavelength() {
            return this != SKY;
        }

        float speed() {
            GamingConfig c = GamingConfig.INSTANCE;
            return switch (this) {
                case BLOCKS -> c.blockSpeed;
                case SKY -> c.skySpeed;
                case ENTITIES -> c.entitySpeed;
                case HANDS -> c.handSpeed;
                case ITEMS -> c.itemSpeed;
                case GUI -> c.guiSpeed;
            };
        }

        void speed(float value) {
            GamingConfig c = GamingConfig.INSTANCE;
            value = GamingConfig.clampSpeed(value);
            switch (this) {
                case BLOCKS -> c.blockSpeed = value;
                case SKY -> c.skySpeed = value;
                case ENTITIES -> c.entitySpeed = value;
                case HANDS -> c.handSpeed = value;
                case ITEMS -> c.itemSpeed = value;
                case GUI -> c.guiSpeed = value;
            }
        }

        float wavelength() {
            GamingConfig c = GamingConfig.INSTANCE;
            return switch (this) {
                case BLOCKS -> c.blockWavelength;
                case ENTITIES -> c.entityWavelength;
                case HANDS -> c.handWavelength;
                case ITEMS -> c.itemWavelength;
                case GUI -> c.guiWavelength;
                case SKY -> 1.0F;
            };
        }

        void wavelength(float value) {
            GamingConfig c = GamingConfig.INSTANCE;
            value = GamingConfig.clampWavelength(value);
            switch (this) {
                case BLOCKS -> c.blockWavelength = value;
                case ENTITIES -> c.entityWavelength = value;
                case HANDS -> c.handWavelength = value;
                case ITEMS -> c.itemWavelength = value;
                case GUI -> c.guiWavelength = value;
                case SKY -> { }
            }
        }
    }
}
