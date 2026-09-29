#version 150
#moj_import <gaming.glsl>
uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;
in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;
void main() {
    vec4 sampled = texture(Sampler0, texCoord0);
    if (sampled.a == 0.0) discard;
    vec4 color = vec4(ColorModulator.rgb * vertexColor.rgb, ColorModulator.a);
    float phase = GamingParams.w * GamingParams.x
        + (texCoord0.x * 3.2 + texCoord0.y * 2.4) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    fragColor = color;
}
