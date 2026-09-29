bool gaming_flag(int bit) {
    return (UseRgss & (1 << bit)) != 0;
}

float gaming_speed(int shift) {
    float encoded = float((UseRgss >> shift) & 31) / 31.0;
    return mix(0.25, 20.0, encoded);
}

float gaming_wavelength(int shift) {
    float encoded = float((MenuBlurRadius >> shift) & 63) / 63.0;
    return mix(0.25, 4.0, encoded);
}

float gaming_gui_wavelength() {
    float encoded = float((MenuBlurRadius >> 28) & 15) / 15.0;
    return mix(0.25, 4.0, encoded);
}

float gaming_gui_speed() {
    float encoded = float((UseRgss >> 26) & 7) / 7.0;
    return mix(0.25, 20.0, encoded);
}

vec3 gaming_rainbow(float phase) {
    return 0.5 + 0.5 * cos(6.2831853 * (phase + vec3(0.0, 0.3333333, 0.6666667)));
}

float gaming_phase(vec3 position, float speed, float wavelength) {
    float seconds = GameTime * 1200.0;
    float spatial = dot(position, vec3(0.021, 0.014, 0.027)) / max(0.25, wavelength);
    return seconds * speed * 0.18 + spatial;
}

vec3 gaming_colorize(vec3 base, vec3 position, float speed, float wavelength) {
    vec3 rainbow = gaming_rainbow(gaming_phase(position, speed, wavelength));
    float luminance = max(dot(base, vec3(0.299, 0.587, 0.114)), 0.24);
    vec3 bright = mix(base * (0.68 + rainbow * 1.38),
                      rainbow * (0.52 + luminance * 1.58), 0.78);
    return mix(base, bright, GlintAlpha);
}
