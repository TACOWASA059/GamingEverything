package com.github.tacowasa059.gamingeverything.client;

import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.phys.Vec3;

public final class GamingShaderController {
    private GamingShaderController() {}

    public static void applyWorldSettings() {
        GamingConfig c = GamingConfig.INSTANCE;
        Vec3 camera = camera();
        setBlocks(c.enabled && c.worldWash, c.blockSpeed, c.blockWavelength, camera);
        setEntities(c.enabled && c.entities, c.entitySpeed, c.entityWavelength, camera);
        setItems(c.enabled && c.items, c.itemSpeed, c.itemWavelength, camera);
        setSpecial(c.enabled && c.specialEffects, c.entitySpeed, c.entityWavelength, camera);
    }

    public static void applyFirstPersonSettings() {
        GamingConfig c = GamingConfig.INSTANCE;
        Vec3 camera = camera();
        setBlocks(c.enabled && c.items, c.itemSpeed, c.itemWavelength, camera);
        setEntities(c.enabled && c.hands, c.handSpeed, c.handWavelength, camera);
        setItems(c.enabled && c.items, c.itemSpeed, c.itemWavelength, camera);
    }

    private static void setBlocks(boolean enabled, float speed, float wavelength, Vec3 camera) {
        set(GameRenderer.getRendertypeSolidShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeCutoutMippedShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeCutoutShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeTranslucentShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeCrumblingShader(), enabled, speed, wavelength, camera);
    }

    private static void setEntities(boolean enabled, float speed, float wavelength, Vec3 camera) {
        set(GameRenderer.getRendertypeEntitySolidShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityCutoutShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityCutoutNoCullShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityCutoutNoCullZOffsetShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityTranslucentShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityTranslucentCullShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityTranslucentEmissiveShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntitySmoothCutoutShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeArmorCutoutNoCullShader(), enabled, speed, wavelength, camera);
    }

    private static void setItems(boolean enabled, float speed, float wavelength, Vec3 camera) {
        set(GameRenderer.getRendertypeItemEntityTranslucentCullShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeGlintShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeGlintDirectShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeGlintTranslucentShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityGlintShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEntityGlintDirectShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeArmorGlintShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeArmorEntityGlintShader(), enabled, speed, wavelength, camera);
    }

    private static void setSpecial(boolean enabled, float speed, float wavelength, Vec3 camera) {
        set(GameRenderer.getRendertypeEyesShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEnergySwirlShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeBeaconBeamShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeLeashShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeTextShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeTextSeeThroughShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEndPortalShader(), enabled, speed, wavelength, camera);
        set(GameRenderer.getRendertypeEndGatewayShader(), enabled, speed, wavelength, camera);
    }

    private static void set(ShaderInstance shader, boolean enabled, float speed,
                            float wavelength, Vec3 camera) {
        if (shader == null) return;
        Uniform params = shader.getUniform("GamingParams");
        if (params != null) {
            float seconds = (System.nanoTime() % 1_000_000_000_000L) / 1_000_000_000.0F;
            params.set(GamingConfig.animationRate(speed), GamingConfig.INSTANCE.intensity,
                    enabled ? 1.0F : 0.0F, seconds);
        }
        Uniform spatial = shader.getUniform("GamingSpatialScale");
        if (spatial != null) spatial.set(1.0F / GamingConfig.clampWavelength(wavelength));
        Uniform cameraUniform = shader.getUniform("GamingCamera");
        if (cameraUniform != null) {
            cameraUniform.set((float) camera.x, (float) camera.y, (float) camera.z);
        }
    }

    private static Vec3 camera() {
        return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
    }
}
