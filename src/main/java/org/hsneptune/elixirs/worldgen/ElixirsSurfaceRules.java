package org.hsneptune.elixirs.worldgen;


import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ElixirsSurfaceRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule GLOWING_FOLIAGE = makeStateRule(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE);

    public static MaterialRules.MaterialRule makeRule() {
        // Condition: we're in your custom biome
        MaterialRules.MaterialCondition inGlowingCaves =
            MaterialRules.biome(ElixirsBiomes.GLOWING_MUSHROOM_CAVES);

        // Replace ALL stone (including deepslate) with your foliage in your biome
        MaterialRules.MaterialRule replaceAllStone = MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.surface(), GLOWING_FOLIAGE)
                );

        return MaterialRules.sequence(
            MaterialRules.condition(inGlowingCaves, replaceAllStone)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
