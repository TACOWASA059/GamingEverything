#version 150

#moj_import <fog.glsl>
#moj_import <gaming.glsl>

uniform sampler2D Sampler0;
uniform mat4 ProjMat;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 gamingUv;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    float phase = GamingParams.w * GamingParams.x
        + (gamingUv.x * 2.0 + gamingUv.y * 4.0) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    float fragmentDistance = -ProjMat[3].z / (gl_FragCoord.z * -2.0 + 1.0 - ProjMat[2].z);
    fragColor = linear_fog(color, fragmentDistance, FogStart, FogEnd, FogColor);
}
