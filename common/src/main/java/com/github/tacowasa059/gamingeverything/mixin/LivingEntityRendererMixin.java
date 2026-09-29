package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    private static final ThreadLocal<Float> GAMING_PHASE = new ThreadLocal<>();

    @Inject(method = "render", at = @At("HEAD"))
    private void gamingeverything$beginEntity(LivingEntity entity, float yaw, float partialTick,
                                               PoseStack poseStack, MultiBufferSource buffers,
                                               int packedLight, CallbackInfo ci) {
        GAMING_PHASE.set((entity.getId() * 0.17320508F) % 1.0F);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void gamingeverything$endEntity(LivingEntity entity, float yaw, float partialTick,
                                             PoseStack poseStack, MultiBufferSource buffers,
                                             int packedLight, CallbackInfo ci) {
        GAMING_PHASE.remove();
    }

    private static float phase() {
        Float value = GAMING_PHASE.get();
        return value == null ? 0.0F : value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), index = 4)
    private float gamingeverything$tintEntityRed(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.entities
                ? RainbowColor.tint(original, phase(), 0, c.intensity, c.entitySpeed) : original;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), index = 5)
    private float gamingeverything$tintEntityGreen(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.entities
                ? RainbowColor.tint(original, phase(), 1, c.intensity, c.entitySpeed) : original;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), index = 6)
    private float gamingeverything$tintEntityBlue(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.entities
                ? RainbowColor.tint(original, phase(), 2, c.intensity, c.entitySpeed) : original;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), index = 2)
    private int gamingeverything$brightEntity(int original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.entities ? LightTexture.FULL_BRIGHT : original;
    }
}
