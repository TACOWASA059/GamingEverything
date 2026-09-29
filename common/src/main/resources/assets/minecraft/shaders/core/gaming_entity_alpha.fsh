#version 150
#moj_import <gaming.glsl>
uniform sampler2D Sampler0;
uniform vec4 GamingParams;
uniform float GamingSpatialScale;
in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord1;
in vec2 texCoord2;
out vec4 fragColor;
void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < vertexColor.a) discard;
    float phase = GamingParams.w * GamingParams.x
        + (texCoord0.x * 3.2 + texCoord0.y * 2.4) * GamingSpatialScale;
    color.rgb = gaming_colorize(color.rgb, phase, GamingParams);
    fragColor = color;
}
