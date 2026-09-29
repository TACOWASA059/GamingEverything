package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {
    private static final ThreadLocal<Float> GAMING_PHASE = new ThreadLocal<>();

    @Inject(method = "putQuadData", at = @At("HEAD"))
    private void gamingeverything$beginBlock(BlockAndTintGetter level, BlockState state, BlockPos pos,
                                              VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quad,
                                              float brightness0, float brightness1, float brightness2, float brightness3,
                                              int light0, int light1, int light2, int light3, int overlay,
                                              CallbackInfo ci) {
        float spatial = pos.getX() * 0.071F + pos.getY() * 0.047F + pos.getZ() * 0.093F;
        GAMING_PHASE.set(spatial + quad.getDirection().ordinal() * 0.137F);
    }

    @Inject(method = "putQuadData", at = @At("RETURN"))
    private void gamingeverything$endBlock(BlockAndTintGetter level, BlockState state, BlockPos pos,
                                            VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quad,
                                            float brightness0, float brightness1, float brightness2, float brightness3,
                                            int light0, int light1, int light2, int light3, int overlay,
                                            CallbackInfo ci) {
        GAMING_PHASE.remove();
    }

    private static float phase() {
        Float value = GAMING_PHASE.get();
        return value == null ? 0.30F : value;
    }

    @ModifyArg(method = "putQuadData", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFF[IIZ)V"), index = 3)
    private float gamingeverything$tintBlockRed(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.worldWash
                ? RainbowColor.tint(original, phase(), 0, c.intensity * 0.52F) : original;
    }

    @ModifyArg(method = "putQuadData", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFF[IIZ)V"), index = 4)
    private float gamingeverything$tintBlockGreen(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.worldWash
                ? RainbowColor.tint(original, phase(), 1, c.intensity * 0.52F) : original;
    }

    @ModifyArg(method = "putQuadData", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFF[IIZ)V"), index = 5)
    private float gamingeverything$tintBlockBlue(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.worldWash
                ? RainbowColor.tint(original, phase(), 2, c.intensity * 0.52F) : original;
    }
}
