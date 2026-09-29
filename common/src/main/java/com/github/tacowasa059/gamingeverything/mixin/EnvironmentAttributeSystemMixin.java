package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.SpatialAttributeInterpolator;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnvironmentAttributeSystem.class)
public abstract class EnvironmentAttributeSystemMixin {
    @Inject(method = "getDimensionValue", at = @At("RETURN"), cancellable = true)
    private <Value> void gamingeverything$dimensionColor(EnvironmentAttribute<Value> attribute,
                                                          CallbackInfoReturnable<Value> cir) {
        cir.setReturnValue(gamingeverything$color(attribute, cir.getReturnValue()));
    }

    @Inject(method = "getValue", at = @At("RETURN"), cancellable = true)
    private <Value> void gamingeverything$positionalColor(EnvironmentAttribute<Value> attribute, Vec3 pos,
                                                           @Nullable SpatialAttributeInterpolator interpolator,
                                                           CallbackInfoReturnable<Value> cir) {
        cir.setReturnValue(gamingeverything$color(attribute, cir.getReturnValue()));
    }

    @SuppressWarnings("unchecked")
    private static <Value> Value gamingeverything$color(EnvironmentAttribute<Value> attribute, Value original) {
        GamingConfig c = GamingConfig.INSTANCE;
        if (!c.enabled || !(original instanceof Integer base)) return original;

        if (attribute == EnvironmentAttributes.SKY_COLOR && c.rainbowSky) {
            return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.0F, c.skySpeed), c.intensity * 0.46F, false));
        }
        if (attribute == EnvironmentAttributes.CLOUD_COLOR && c.specialEffects) {
            return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.12F, c.skySpeed), c.intensity * 0.72F, true));
        }
        if (attribute == EnvironmentAttributes.FOG_COLOR && c.rainbowSky) {
            return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.28F, c.skySpeed), c.intensity * 0.24F, false));
        }
        return original;
    }

    private static int gamingeverything$blend(int base, int rainbow, float amount, boolean preserveAlpha) {
        int alpha = preserveAlpha ? ARGB.alpha(base) : 255;
        int red = Mth.clamp(Math.round(ARGB.red(base) + (ARGB.red(rainbow) - ARGB.red(base)) * amount), 0, 255);
        int green = Mth.clamp(Math.round(ARGB.green(base) + (ARGB.green(rainbow) - ARGB.green(base)) * amount), 0, 255);
        int blue = Mth.clamp(Math.round(ARGB.blue(base) + (ARGB.blue(rainbow) - ARGB.blue(base)) * amount), 0, 255);
        return preserveAlpha ? ARGB.color(alpha, red, green, blue) : (red << 16) | (green << 8) | blue;
    }
}
