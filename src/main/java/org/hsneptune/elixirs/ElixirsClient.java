
package org.hsneptune.elixirs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
import java.util.Map;

import org.hsneptune.elixirs.hud.HudRenderInvoker;

import java.util.HashMap;

public class ElixirsClient implements ClientModInitializer {
    private static final Map<Block, BlockRenderLayer> blockTransparency = new HashMap<>();

    @Override
    public void onInitializeClient(){
		HudRenderInvoker.start();
        blockTransparency.forEach(BlockRenderLayerMap::putBlock);
    }

    public static void addTransparentBlock(Block block, BlockRenderLayer renderLayer) {
       blockTransparency.put(block, renderLayer); 
    }
}
