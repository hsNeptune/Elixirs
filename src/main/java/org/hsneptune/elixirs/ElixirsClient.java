
package org.hsneptune.elixirs;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.hsneptune.elixirs.client.HudOverlays;

public class ElixirsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(){
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            HudOverlays.render(context);
        });

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            HudOverlays.renderAffinity(context);
        });
    }
}
