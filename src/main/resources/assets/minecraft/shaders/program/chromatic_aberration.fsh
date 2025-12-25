#version 150

in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D DiffuseSampler;
uniform float offset_;

void main() {

    float r = texture(DiffuseSampler, texCoord - vec2(offset_, 0)).r;
    float g = texture(DiffuseSampler, texCoord).g;
    float b = texture(DiffuseSampler, texCoord + vec2(offset_, 0)).b;


    fragColor = vec4(r, g, b, 1);
}
