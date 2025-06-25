
package org.hsneptune.elixirs;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.hsneptune.elixirs.client.RageHudOverlay;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElixirsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            RageHudOverlay.render(context);
        });
    }
}
