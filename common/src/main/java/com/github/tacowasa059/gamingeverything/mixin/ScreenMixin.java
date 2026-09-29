package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingGuiRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void gamingeverything$renderGamingScreenFrame(GuiGraphics graphics, int mouseX, int mouseY,
                                                           float partialTick, CallbackInfo ci) {
        GamingGuiRenderer.renderScreenFrame(graphics);
    }
}
