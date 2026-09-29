package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingGuiRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
    @Shadow protected int imageWidth;
    @Shadow protected int imageHeight;
    @Shadow protected int leftPos;
    @Shadow protected int topPos;
    @Shadow @Final protected AbstractContainerMenu menu;

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void gamingeverything$renderGamingContainerFrame(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
                                                              float partialTick, CallbackInfo ci) {
        GamingGuiRenderer.renderContainerFrame(graphics, leftPos, topPos, imageWidth, imageHeight);
        for (Slot slot : menu.slots) {
            if (slot.isActive()) {
                GamingGuiRenderer.renderSlotFrame(graphics, leftPos + slot.x, topPos + slot.y, 16);
            }
        }
    }
}
