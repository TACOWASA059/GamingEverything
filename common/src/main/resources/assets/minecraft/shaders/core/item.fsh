#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:gaming.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
#ifdef ALPHA_CUTOUT
    if (color.a < ALPHA_CUTOUT) {
        discard;
    }
#endif
    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    if (gaming_flag(0) && gaming_flag(4)) {
        // Texture-space coordinates keep the rainbow attached to the item and
        // remain compatible with loader-provided vertex shader variants.
        color.rgb = gaming_colorize(color.rgb, vec3(texCoord0 * 48.0, 0.0), gaming_speed(21), gaming_wavelength(22));
    }
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
