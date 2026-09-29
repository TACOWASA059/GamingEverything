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
out vec4 fragColor;
void main() {
    vec4 color = vertexColor * ColorModulator;
    float phase = GamingParams.w * GamingParams.x
        + dot(vertexColor.rgb, vec3(0.31, 0.53, 0.79)) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
