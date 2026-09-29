package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingShaderController;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Inject(method = "renderHandsWithItems", at = @At("HEAD"))
    private void gamingeverything$useFirstPersonSettings(float partialTick, PoseStack poseStack,
                                                         MultiBufferSource.BufferSource buffers,
                                                         LocalPlayer player, int light,
                                                         CallbackInfo ci) {
        GamingShaderController.applyFirstPersonSettings();
    }

    @Inject(method = "renderHandsWithItems", at = @At("RETURN"))
    private void gamingeverything$restoreWorldSettings(float partialTick, PoseStack poseStack,
                                                        MultiBufferSource.BufferSource buffers,
                                                        LocalPlayer player, int light,
                                                        CallbackInfo ci) {
        GamingShaderController.applyWorldSettings();
    }
}
