
package org.hsneptune.elixirs;

import java.util.HashMap;
import java.util.Map;
import org.hsneptune.elixirs.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;

public class ElixirsClient implements ClientModInitializer {
    private static final Map<Block, RenderLayer> blockTransparency = new HashMap<>();

    @Override
    public void onInitializeClient(){
		ChromaticAberration.initShader();
        blockTransparency.forEach(BlockRenderLayerMap.INSTANCE::putBlock);
    }

    public static void addTransparentBlock(Block block, RenderLayer renderLayer) {
       blockTransparency.put(block, renderLayer); 
    }
}
