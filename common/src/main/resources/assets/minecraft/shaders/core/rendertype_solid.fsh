#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform vec4 GamingParams;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in vec3 gamingPosition;

out vec4 fragColor;

vec3 gamingRainbow(float phase) {
    return 0.5 + 0.5 * cos(6.2831853 * (phase + vec3(0.0, 0.3333333, 0.6666667)));
}

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    float spatial = gamingPosition.x * 0.041 + gamingPosition.z * 0.053 + gamingPosition.y * 0.019;
    vec3 rainbow = gamingRainbow(GamingParams.w * GamingParams.x + spatial);
    float luminance = max(dot(color.rgb, vec3(0.299, 0.587, 0.114)), 0.18);
    vec3 gamingColor = mix(color.rgb * (0.65 + rainbow * 1.25),
                           rainbow * (0.38 + luminance * 1.55), 0.68);
    color.rgb = mix(color.rgb, gamingColor, GamingParams.y * GamingParams.z);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
