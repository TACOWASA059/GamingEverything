#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;
uniform float GamingAlphaCutoff;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec3 gamingPosition;

out vec4 fragColor;

vec3 gamingRainbow(float phase) {
    return 0.5 + 0.5 * cos(6.2831853 * (phase + vec3(0.0, 0.3333333, 0.6666667)));
}

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < GamingAlphaCutoff) discard;
    float spatial = (gamingPosition.x * 0.021 + gamingPosition.z * 0.027
                     + gamingPosition.y * 0.014) * GamingSpatialScale;
    float travel = GamingParams.w * GamingParams.x;
    vec3 rainbow = gamingRainbow(travel + spatial);
    float luminance = max(dot(color.rgb, vec3(0.299, 0.587, 0.114)), 0.20);
    vec3 gamingColor = mix(color.rgb * (0.70 + rainbow * 1.30),
                           rainbow * (0.42 + luminance * 1.65), 0.72);
    color.rgb = mix(color.rgb, gamingColor, GamingParams.y * GamingParams.z);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
