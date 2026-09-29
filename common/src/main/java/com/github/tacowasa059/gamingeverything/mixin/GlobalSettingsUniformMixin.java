package com.github.tacowasa059.gamingeverything.mixin;

import com.github.tacowasa059.gamingeverything.client.GamingConfig;
import com.github.tacowasa059.gamingeverything.client.GamingUniformEncoding;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GlobalSettingsUniform;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GlobalSettingsUniform.class)
public abstract class GlobalSettingsUniformMixin {
    @Shadow @Final private GpuBuffer buffer;

    /**
     * Stores the client-only gaming controls in otherwise vanilla-compatible global fields.
     * The replacement shaders decode these values every frame, so toggles and animation
     * sliders update without rebuilding chunks and without using camera-relative phase.
     */
    @Overwrite
    public void update(int width, int height, double glintAlpha, long gameTime, DeltaTracker deltaTracker,
                       int menuBlurRadius, Vec3 cameraPos, boolean useRgss) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int cameraX = Mth.floor(cameraPos.x);
            int cameraY = Mth.floor(cameraPos.y);
            int cameraZ = Mth.floor(cameraPos.z);
            ByteBuffer data = Std140Builder.onStack(stack, GlobalSettingsUniform.UBO_SIZE)
                    .putIVec3(cameraX, cameraY, cameraZ)
                    .putVec3((float) (cameraX - cameraPos.x), (float) (cameraY - cameraPos.y),
                            (float) (cameraZ - cameraPos.z))
                    .putVec2(width, height)
                    .putFloat(GamingConfig.INSTANCE.intensity)
                    .putFloat(((float) (gameTime % 24000L)
                            + deltaTracker.getGameTimeDeltaPartialTick(false)) / 24000.0F)
                    .putInt(GamingUniformEncoding.blurAndWavelengths(menuBlurRadius))
                    .putInt(GamingUniformEncoding.flagsAndSpeeds(useRgss))
                    .get();
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), data);
        }
        RenderSystem.setGlobalSettingsUniform(this.buffer);
    }
}
