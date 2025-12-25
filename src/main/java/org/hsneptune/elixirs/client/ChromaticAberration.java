
package org.hsneptune.elixirs.client;

import org.hsneptune.elixirs.effects.ElixirsEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;

public class ChromaticAberration {


private static PostEffectProcessor chromaticShader;

public static void initShader() {

    MinecraftClient client = MinecraftClient.getInstance();
}

public static void drawChromaticOverlay(float offset) {
	offset = 0.005f;
    MinecraftClient client = MinecraftClient.getInstance();
    Framebuffer fb = client.getFramebuffer();
    
    // If framebuffer isn't ready, do nothing
    if (fb == null || client.player == null) return;

	if (client.currentScreen instanceof GameMenuScreen) return;
    // Lazy initialize shader only once
    if (chromaticShader == null) {
        try {
            chromaticShader = new PostEffectProcessor(
                client.getTextureManager(),
                client.getResourceManager(),
                fb,
                Identifier.of("elixirs", "shaders/post/chromatic_aberration.json")
            );
            chromaticShader.setupDimensions(
                client.getWindow().getFramebufferWidth(),
                client.getWindow().getFramebufferHeight()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    // Check if the player has the effect
    StatusEffectInstance effect = client.player.getStatusEffect(ElixirsEffects.TEMPORAL_DISSONANCE);
    if (effect == null) return;

	int remainingTicks = effect.getDuration();
	
	if (remainingTicks < 200) {
		offset *= ((float) remainingTicks / 200f);
	}

    // Apply the shader
    chromaticShader.setUniforms("offset_", offset);
    chromaticShader.render(1);
}

    public static void updateShaderDimensions() {
		if (chromaticShader == null) return;
		MinecraftClient client = MinecraftClient.getInstance();
        int lastWidth = client.getWindow().getFramebufferWidth();
        int lastHeight = client.getWindow().getFramebufferHeight();
        chromaticShader.setupDimensions(lastWidth, lastHeight);
    }
}
