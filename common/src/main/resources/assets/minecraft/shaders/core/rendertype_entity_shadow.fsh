#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:gaming.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, clamp(texCoord0, 0.0, 1.0)) * vertexColor * ColorModulator;
    if (gaming_flag(0) && gaming_flag(2)) {
        color.rgb = gaming_colorize(color.rgb, vec3(texCoord0 * 32.0, 0.0),
            gaming_speed(11), gaming_wavelength(10));
    }
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
