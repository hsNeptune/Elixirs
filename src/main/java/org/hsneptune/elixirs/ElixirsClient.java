
package org.hsneptune.elixirs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.mixin.client.rendering.InGameHudMixin;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import org.hsneptune.elixirs.client.HudOverlays;
import java.util.Map;
import java.util.HashMap;

public class ElixirsClient implements ClientModInitializer {
    private static final Map<Block, RenderLayer> blockTransparency = new HashMap<>();

    @Override
    public void onInitializeClient(){
        blockTransparency.forEach(BlockRenderLayerMap.INSTANCE::putBlock);
    }

    public static void addTransparentBlock(Block block, RenderLayer renderLayer) {
       blockTransparency.put(block, renderLayer); 
    }
}
