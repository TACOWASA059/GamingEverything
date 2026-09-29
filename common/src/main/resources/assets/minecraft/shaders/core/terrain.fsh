#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:chunksection.glsl>
#moj_import <minecraft:gaming.glsl>
uniform sampler2D Sampler0;
in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec3 gamingPosition;
out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;
    color = mix(FogColor * vec4(1.0, 1.0, 1.0, color.a), color, ChunkVisibility);
#ifdef ALPHA_CUTOUT
    if (color.a < ALPHA_CUTOUT) discard;
#endif
    if (gaming_flag(0) && gaming_flag(1)) {
        color.rgb = gaming_colorize(color.rgb, gamingPosition, gaming_speed(6), gaming_wavelength(4));
    }
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
