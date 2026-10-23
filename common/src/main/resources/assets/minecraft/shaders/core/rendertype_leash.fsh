#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:globals.glsl>
#include <minecraft:gaming.glsl>

layout(location = 0) in float sphericalVertexDistance;
layout(location = 1) in float cylindricalVertexDistance;
layout(location = 2) flat in vec4 vertexColor;

layout(location = 0) out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (gaming_flag(0) && gaming_flag(30)) {
        color.rgb = gaming_colorize(color.rgb, vec3(vertexColor.rgb * 16.0),
            gaming_speed(11), gaming_wavelength(10));
    }
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
