#version 330

#ifndef IS_SEE_THROUGH
#moj_import <minecraft:fog.glsl>
#endif

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:gaming.glsl>

#ifndef IS_SEE_THROUGH
in float sphericalVertexDistance;
in float cylindricalVertexDistance;
#endif

in vec4 vertexColor;

out vec4 fragColor;

void main() {
#ifdef IS_SEE_THROUGH
    vec4 color = vertexColor;
#else
    vec4 color = vertexColor * ColorModulator;
#endif
    if (color.a < 0.1) {
        discard;
    }
    if (gaming_flag(0) && gaming_flag(5)) {
        color.rgb = gaming_colorize(color.rgb, vec3(0.0), gaming_gui_speed(), gaming_gui_wavelength());
    }
#ifdef IS_SEE_THROUGH
    fragColor = color * ColorModulator;
#else
    fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
#endif
}
