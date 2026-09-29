#version 150

#moj_import <gaming.glsl>

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 gamingUv;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0).rrrr * vertexColor * ColorModulator;
    if (color.a < 0.1) discard;
    float phase = GamingParams.w * GamingParams.x
        + (gamingUv.x * 3.2 + gamingUv.y * 2.4) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    fragColor = color;
}
