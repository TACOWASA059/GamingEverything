vec3 gaming_rainbow(float phase) {
    return 0.5 + 0.5 * cos(6.2831853 * (phase + vec3(0.0, 0.3333333, 0.6666667)));
}

vec3 gaming_colorize(vec3 base, float phase, vec4 params) {
    vec3 rainbow = gaming_rainbow(phase);
    float luminance = max(dot(base, vec3(0.299, 0.587, 0.114)), 0.24);
    vec3 bright = mix(base * (0.68 + rainbow * 1.38),
                      rainbow * (0.52 + luminance * 1.58), 0.78);
    return mix(base, bright, params.y * params.z);
}
