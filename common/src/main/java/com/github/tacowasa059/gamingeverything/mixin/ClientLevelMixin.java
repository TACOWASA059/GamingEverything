package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void gamingeverything$rainbowSky(Vec3 position, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.rainbowSky) return;
        Vec3 original = cir.getReturnValue();
        float amount = config.intensity * 0.38F;
        cir.setReturnValue(new Vec3(
                original.x + (RainbowColor.channel(0.0F, 0, config.skySpeed) - original.x) * amount,
                original.y + (RainbowColor.channel(0.0F, 1, config.skySpeed) - original.y) * amount,
                original.z + (RainbowColor.channel(0.0F, 2, config.skySpeed) - original.z) * amount));
    }

    @Inject(method = "getCloudColor", at = @At("RETURN"), cancellable = true)
    private void gamingeverything$rainbowClouds(float partialTick, CallbackInfoReturnable<Vec3> cir) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.specialEffects) return;
        Vec3 original = cir.getReturnValue();
        float amount = config.intensity * 0.72F;
        cir.setReturnValue(new Vec3(
                original.x + (RainbowColor.channel(0.12F, 0, config.skySpeed) - original.x) * amount,
                original.y + (RainbowColor.channel(0.12F, 1, config.skySpeed) - original.y) * amount,
                original.z + (RainbowColor.channel(0.12F, 2, config.skySpeed) - original.z) * amount));
    }
}
