package org.hsneptune.elixirs.hud;

import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.client.HudOverlays;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class HudRenderInvoker {
	public static void start() {
		// for some reason a lambda isn't working here
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, 
				Identifier.of(Elixirs.MOD_ID, "overlay_render"), new HudElement () {
					@Override
					public void render(DrawContext dcContext, RenderTickCounter rCounter) { HudOverlays.render(dcContext, rCounter);}
				});
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, 
				Identifier.of(Elixirs.MOD_ID, "overlay_render"), new HudElement () {
					@Override
					public void render(DrawContext dcContext, RenderTickCounter rCounter) { HudOverlays.renderAffinity(dcContext, rCounter);}
				});
	}
}
