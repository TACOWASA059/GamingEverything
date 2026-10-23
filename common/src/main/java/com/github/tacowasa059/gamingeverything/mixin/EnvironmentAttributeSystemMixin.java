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
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
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
        if (!c.enabled || original == null) return original;

        if (original instanceof Vector3fc vec3) {
            if (attribute == EnvironmentAttributes.SKY_COLOR && c.rainbowSky) {
                return (Value) gamingeverything$blend(vec3, RainbowColor.vector(0.0F, c.skySpeed), c.intensity * 0.46F);
            }
            if (attribute == EnvironmentAttributes.FOG_COLOR && c.rainbowSky) {
                return (Value) gamingeverything$blend(vec3, RainbowColor.vector(0.28F, c.skySpeed), c.intensity * 0.24F);
            }
            if (attribute == EnvironmentAttributes.WATER_FOG_COLOR && c.rainbowSky) {
                return (Value) gamingeverything$blend(vec3, RainbowColor.vector(0.28F, c.skySpeed), c.intensity * 0.24F);
            }
        } else if (original instanceof Vector4fc vec4) {
            if (attribute == EnvironmentAttributes.CLOUD_COLOR && c.specialEffects) {
                return (Value) gamingeverything$blend(vec4, RainbowColor.vector(0.12F, c.skySpeed), c.intensity * 0.72F, true);
            }
            if (attribute == EnvironmentAttributes.SUNRISE_SUNSET_COLOR && c.rainbowSky) {
                return (Value) gamingeverything$blend(vec4, RainbowColor.vector(0.0F, c.skySpeed), c.intensity * 0.46F, true);
            }
        } else if (original instanceof Integer base) {
            if (attribute == EnvironmentAttributes.SKY_COLOR && c.rainbowSky) {
                return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.0F, c.skySpeed), c.intensity * 0.46F, false));
            }
            if (attribute == EnvironmentAttributes.CLOUD_COLOR && c.specialEffects) {
                return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.12F, c.skySpeed), c.intensity * 0.72F, true));
            }
            if (attribute == EnvironmentAttributes.FOG_COLOR && c.rainbowSky) {
                return (Value) Integer.valueOf(gamingeverything$blend(base, RainbowColor.rgb(0.28F, c.skySpeed), c.intensity * 0.24F, false));
            }
        }
        return original;
    }

    private static Vector3f gamingeverything$blend(Vector3fc base, Vector3f rainbow, float amount) {
        float r = Mth.clamp(Mth.lerp(amount, base.x(), rainbow.x()), 0.0F, 1.0F);
        float g = Mth.clamp(Mth.lerp(amount, base.y(), rainbow.y()), 0.0F, 1.0F);
        float b = Mth.clamp(Mth.lerp(amount, base.z(), rainbow.z()), 0.0F, 1.0F);
        return new Vector3f(r, g, b);
    }

    private static Vector4f gamingeverything$blend(Vector4fc base, Vector3f rainbow, float amount, boolean preserveAlpha) {
        float r = Mth.clamp(Mth.lerp(amount, base.x(), rainbow.x()), 0.0F, 1.0F);
        float g = Mth.clamp(Mth.lerp(amount, base.y(), rainbow.y()), 0.0F, 1.0F);
        float b = Mth.clamp(Mth.lerp(amount, base.z(), rainbow.z()), 0.0F, 1.0F);
        float a = preserveAlpha ? base.w() : 1.0F;
        return new Vector4f(r, g, b, a);
    }

    private static int gamingeverything$blend(int base, int rainbow, float amount, boolean preserveAlpha) {
        int alpha = preserveAlpha ? ARGB.alpha(base) : 255;
        int red = Mth.clamp(Math.round(ARGB.red(base) + (ARGB.red(rainbow) - ARGB.red(base)) * amount), 0, 255);
        int green = Mth.clamp(Math.round(ARGB.green(base) + (ARGB.green(rainbow) - ARGB.green(base)) * amount), 0, 255);
        int blue = Mth.clamp(Math.round(ARGB.blue(base) + (ARGB.blue(rainbow) - ARGB.blue(base)) * amount), 0, 255);
        return preserveAlpha ? ARGB.color(alpha, red, green, blue) : (red << 16) | (green << 8) | blue;
    }
}
