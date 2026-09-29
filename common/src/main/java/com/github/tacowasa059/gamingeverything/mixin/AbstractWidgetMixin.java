package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingGuiRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void gamingeverything$renderGamingFrame(GuiGraphics graphics, int mouseX, int mouseY,
                                                     float partialTick, CallbackInfo ci) {
        AbstractWidget widget = (AbstractWidget) (Object) this;
        if (!widget.visible) return;
        GamingGuiRenderer.renderWidgetFrame(graphics, widget.getX(), widget.getY(),
                widget.getWidth(), widget.getHeight(), widget.isHoveredOrFocused(), widget.active);
    }
}
