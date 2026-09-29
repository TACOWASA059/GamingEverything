#version 150

#moj_import <fog.glsl>
#moj_import <gaming.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;

in float vertexDistance;
in vec4 vertexColor;
in vec3 gamingPosition;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    if (color.a < 0.1) discard;
    float phase = GamingParams.w * GamingParams.x
        + dot(gamingPosition, vec3(0.021, 0.014, 0.027)) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
