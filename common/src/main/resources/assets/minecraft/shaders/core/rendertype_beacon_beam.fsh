#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:gaming.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (gaming_flag(0) && gaming_flag(30)) {
        color.rgb = gaming_colorize(color.rgb, vec3(texCoord0 * 40.0, 0.0),
            gaming_speed(11), gaming_wavelength(10));
    }
    float fragmentDistance = 1.0 / gl_FragCoord.w;
    fragColor = apply_fog(color, fragmentDistance, fragmentDistance,
        FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
