#version 150

#moj_import <fog.glsl>
#moj_import <gaming.glsl>

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform float GlintAlpha;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;

in float vertexDistance;
in vec2 texCoord0;
in vec2 gamingUv;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * ColorModulator;
    if (color.a < 0.1) discard;
    float phase = GamingParams.w * GamingParams.x
        + (gamingUv.x * 4.0 + gamingUv.y * 3.0) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    float fade = linear_fog_fade(vertexDistance, FogStart, FogEnd) * GlintAlpha;
    fragColor = vec4(color.rgb * fade, color.a);
}
