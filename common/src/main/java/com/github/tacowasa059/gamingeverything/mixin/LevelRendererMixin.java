package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingShaderController;
import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    private static final ThreadLocal<Integer> GAMING_WEATHER_VERTEX = new ThreadLocal<>();

    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void gamingeverything$updateGroundShader(CallbackInfo ci) {
        GamingShaderController.applyWorldSettings();
    }

    @Inject(method = "renderSnowAndRain", at = @At("HEAD"))
    private void gamingeverything$beginWeather(CallbackInfo ci) {
        GAMING_WEATHER_VERTEX.set(0);
    }

    @ModifyArg(method = "renderSnowAndRain", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), index = 0)
    private float gamingeverything$rainbowWeatherRed(float original) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.specialEffects) return original;
        int vertex = GAMING_WEATHER_VERTEX.get() == null ? 0 : GAMING_WEATHER_VERTEX.get();
        float phase = vertex * 0.037F;
        float strength = config.intensity * 0.82F;
        return RainbowColor.tint(original, phase, 0, strength, config.skySpeed);
    }

    @ModifyArg(method = "renderSnowAndRain", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), index = 1)
    private float gamingeverything$rainbowWeatherGreen(float original) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.specialEffects) return original;
        int vertex = GAMING_WEATHER_VERTEX.get() == null ? 0 : GAMING_WEATHER_VERTEX.get();
        return RainbowColor.tint(original, vertex * 0.037F, 1,
                config.intensity * 0.82F, config.skySpeed);
    }

    @ModifyArg(method = "renderSnowAndRain", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), index = 2)
    private float gamingeverything$rainbowWeatherBlue(float original) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.specialEffects) return original;
        int vertex = GAMING_WEATHER_VERTEX.get() == null ? 0 : GAMING_WEATHER_VERTEX.get();
        float result = RainbowColor.tint(original, vertex * 0.037F, 2,
                config.intensity * 0.82F, config.skySpeed);
        GAMING_WEATHER_VERTEX.set(vertex + 1);
        return result;
    }

    @Inject(method = "renderSnowAndRain", at = @At("RETURN"))
    private void gamingeverything$endWeather(CallbackInfo ci) {
        GAMING_WEATHER_VERTEX.remove();
    }
}
