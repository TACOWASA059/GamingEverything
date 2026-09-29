package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.RainbowColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    private static final ThreadLocal<Integer> GAMING_QUAD = new ThreadLocal<>();

    @Inject(method = "renderQuadList", at = @At("HEAD"))
    private void gamingeverything$beginItem(PoseStack poseStack, VertexConsumer consumer,
                                             java.util.List<?> quads, ItemStack stack,
                                             int light, int overlay, CallbackInfo ci) {
        GAMING_QUAD.set(0);
    }

    @Inject(method = "renderQuadList", at = @At("RETURN"))
    private void gamingeverything$endItem(PoseStack poseStack, VertexConsumer consumer,
                                           java.util.List<?> quads, ItemStack stack,
                                           int light, int overlay, CallbackInfo ci) {
        GAMING_QUAD.remove();
    }

    private static float quadPhase() {
        Integer value = GAMING_QUAD.get();
        return value == null ? 0.12F : 0.12F + value * 0.145F;
    }
    @ModifyVariable(method = "renderQuadList", at = @At("STORE"), ordinal = 0)
    private float gamingeverything$tintItemRed(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.items
                ? RainbowColor.tint(original, quadPhase(), 0, c.intensity, c.itemSpeed) : original;
    }

    @ModifyVariable(method = "renderQuadList", at = @At("STORE"), ordinal = 1)
    private float gamingeverything$tintItemGreen(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.items
                ? RainbowColor.tint(original, quadPhase(), 1, c.intensity, c.itemSpeed) : original;
    }

    @ModifyVariable(method = "renderQuadList", at = @At("STORE"), ordinal = 2)
    private float gamingeverything$tintItemBlue(float original) {
        GamingConfig c = GamingConfig.INSTANCE;
        float result = c.enabled && c.items
                ? RainbowColor.tint(original, quadPhase(), 2, c.intensity, c.itemSpeed) : original;
        Integer value = GAMING_QUAD.get();
        if (value != null) GAMING_QUAD.set(value + 1);
        return result;
    }

    @ModifyVariable(method = "renderQuadList", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private int gamingeverything$brightItem(int original) {
        GamingConfig c = GamingConfig.INSTANCE;
        return c.enabled && c.items ? LightTexture.FULL_BRIGHT : original;
    }
}
