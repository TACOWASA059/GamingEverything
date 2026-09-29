#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <minecraft:gaming.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 sampled = texture(Sampler0, texCoord0);
    if (sampled.a == 0.0) {
        discard;
    }
    vec4 color = vec4(ColorModulator.rgb * vertexColor.rgb, ColorModulator.a);
    if (gaming_flag(0) && gaming_flag(30)) {
        color.rgb = gaming_colorize(color.rgb, vec3(texCoord0 * 32.0, 0.0),
            gaming_speed(11), gaming_wavelength(10));
    }
    fragColor = color;
}
