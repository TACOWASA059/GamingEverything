#version 150

#moj_import <fog.glsl>
#moj_import <gaming.glsl>

uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;

in float vertexDistance;
flat in vec4 vertexColor;
flat in float gamingVertexPhase;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    color.rgb = gaming_colorize(color.rgb, GamingParams.w * GamingParams.x
        + gamingVertexPhase * GamingSpatialScale, GamingParams);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
