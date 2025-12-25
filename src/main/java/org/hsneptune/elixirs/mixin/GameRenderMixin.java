
package org.hsneptune.elixirs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.LayeredDrawer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.hsneptune.elixirs.client.*;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(GameRenderer.class)
public abstract class GameRenderMixin {


    @Inject(method = "render", at = @At(value = "RETURN",
        target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V" ))
    private void injectChromatic(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		// Chromatic Aberration code
        ChromaticAberration.drawChromaticOverlay(0.03F);
		ChromaticAberration.updateShaderDimensions();

		// If anything else exists let it exist
    }
}

