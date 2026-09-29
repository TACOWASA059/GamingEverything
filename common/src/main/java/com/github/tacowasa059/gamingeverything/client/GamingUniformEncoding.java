package com.github.tacowasa059.gamingeverything.client;

public final class GamingUniformEncoding {
    private GamingUniformEncoding() {}

    public static int flagsAndSpeeds(boolean vanillaRgss) {
        GamingConfig c = GamingConfig.INSTANCE;
        int packed = vanillaRgss ? Integer.MIN_VALUE : 0;
        if (c.enabled) packed |= 1;
        if (c.worldWash) packed |= 1 << 1;
        if (c.entities) packed |= 1 << 2;
        if (c.hands) packed |= 1 << 3;
        if (c.items) packed |= 1 << 4;
        if (c.guiEffects) packed |= 1 << 5;
        packed |= speed(c.blockSpeed) << 6;
        packed |= speed(c.entitySpeed) << 11;
        packed |= speed(c.handSpeed) << 16;
        packed |= speed(c.itemSpeed) << 21;
        packed |= speed3(c.guiSpeed) << 26;
        if (c.particles) packed |= 1 << 29;
        if (c.specialEffects) packed |= 1 << 30;
        return packed;
    }

    public static int blurAndWavelengths(int vanillaBlurRadius) {
        GamingConfig c = GamingConfig.INSTANCE;
        int packed = Math.max(0, Math.min(15, vanillaBlurRadius));
        packed |= wavelength(c.blockWavelength) << 4;
        packed |= wavelength(c.entityWavelength) << 10;
        packed |= wavelength(c.handWavelength) << 16;
        packed |= wavelength(c.itemWavelength) << 22;
        packed |= wavelength4(c.guiWavelength) << 28;
        return packed;
    }

    private static int speed(float value) {
        float normalized = (GamingConfig.clampSpeed(value) - 0.25F) / 19.75F;
        return Math.max(0, Math.min(31, Math.round(normalized * 31.0F)));
    }

    private static int wavelength(float value) {
        float normalized = (GamingConfig.clampWavelength(value) - 0.25F) / 3.75F;
        return Math.max(0, Math.min(63, Math.round(normalized * 63.0F)));
    }

    private static int speed3(float value) {
        float normalized = (GamingConfig.clampSpeed(value) - 0.25F) / 19.75F;
        return Math.max(0, Math.min(7, Math.round(normalized * 7.0F)));
    }

    private static int wavelength4(float value) {
        float normalized = (GamingConfig.clampWavelength(value) - 0.25F) / 3.75F;
        return Math.max(0, Math.min(15, Math.round(normalized * 15.0F)));
    }
}
