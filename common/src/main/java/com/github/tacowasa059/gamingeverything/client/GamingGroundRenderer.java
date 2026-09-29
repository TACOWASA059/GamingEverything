package com.github.tacowasa059.gamingeverything.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public final class GamingGroundRenderer {
    private static final int RADIUS = 14;

    private GamingGroundRenderer() {}

    public static void render(ClientLevel level, Player player, PoseStack poseStack) {
        GamingConfig config = GamingConfig.INSTANCE;
        if (!config.enabled || !config.worldWash) return;

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        int centerX = player.getBlockX();
        int centerY = player.getBlockY();
        int centerZ = player.getBlockZ();
        int alpha = Math.max(145, Math.min(235, Math.round(255.0F * config.intensity)));
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos above = new BlockPos.MutableBlockPos();

        for (int x = centerX - RADIUS; x <= centerX + RADIUS; x++) {
            for (int z = centerZ - RADIUS; z <= centerZ + RADIUS; z++) {
                int top = findSurface(level, cursor, above, x, z, centerY + 8, centerY - 12);
                if (top == Integer.MIN_VALUE) continue;
                double y = top + 1.004D;
                float phase = x * 0.137F + z * 0.173F;
                put(buffer, matrix, x, y, z, phase, alpha);
                put(buffer, matrix, x, y, z + 1, phase + 0.27F, alpha);
                put(buffer, matrix, x + 1, y, z + 1, phase + 0.54F, alpha);
                put(buffer, matrix, x + 1, y, z, phase + 0.81F, alpha);
            }
        }

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferUploader.drawWithShader(buffer.end());
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private static int findSurface(ClientLevel level, BlockPos.MutableBlockPos cursor,
                                   BlockPos.MutableBlockPos above, int x, int z, int startY, int endY) {
        for (int y = startY; y >= endY; y--) {
            cursor.set(x, y, z);
            BlockState state = level.getBlockState(cursor);
            if (state.isAir() || !state.getFluidState().isEmpty() || !state.canOcclude()) continue;
            above.set(x, y + 1, z);
            if (!level.getBlockState(above).canOcclude()) return y;
        }
        return Integer.MIN_VALUE;
    }

    private static void put(BufferBuilder buffer, Matrix4f matrix, double x, double y, double z,
                            float phase, int alpha) {
        int rgb = RainbowColor.rgb(phase);
        buffer.vertex(matrix, (float) x, (float) y, (float) z)
                .color((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255, alpha)
                .endVertex();
    }
}
