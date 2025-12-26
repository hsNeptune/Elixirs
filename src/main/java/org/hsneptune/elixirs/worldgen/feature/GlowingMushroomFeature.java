package org.hsneptune.elixirs.worldgen.feature;

import java.util.*;
import java.util.Random;

import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;
import org.hsneptune.elixirs.worldgen.biome.GlowingMushroomBiome;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class GlowingMushroomFeature extends Feature<DefaultFeatureConfig> {
    public GlowingMushroomFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {

        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        BlockPos belowPos = origin.down(1);
        BlockState belowState = world.getBlockState(belowPos);
    
        try {
            if (!world.getBiome(belowPos).matchesKey(new GlowingMushroomBiome().key())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
        // Check if there's a solid block below to place on
        if (belowState.isAir() || !belowState.isOpaque()) {
            return false;
        }
        
        // Check if the origin position is suitable for placement
        if (!world.getBlockState(origin).isReplaceable()) {
            return false;
        }

        return type1(origin, world);


    }



    public boolean type1(BlockPos origin, StructureWorldAccess world) { 
        var random = new Random();

        Block stalk = ElixirsBlocks.GLOWING_MUSHROOM_STEM;
        Block cap = ElixirsBlocks.GLOWING_MUSHROOM_BLOCK;

        int height = random.nextInt(4, 7);
        int radius = 2;
		Map<BlockPos, BlockState> buffer = new LinkedHashMap<>();

        // Build stalk
        for (int y = 0; y < height; y++) {
            BlockPos pos = new BlockPos(origin.getX(), origin.getY() + y, origin.getZ());
            BlockState state = world.getBlockState(pos);
            if (state.isAir() || state.isReplaceable()) {
				buffer.put(pos, stalk.getDefaultState());
            } else {
				return false;
			}
        }

        // Build cap
        BlockPos capCenter = new BlockPos(origin.getX(), origin.getY() + height, origin.getZ());
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius + 1) {
                    BlockPos capPos = capCenter.add(dx, 0, dz);
                    BlockState state = world.getBlockState(capPos);
                    if (state.isAir() || state.isReplaceable()) {
						buffer.put(capPos, cap.getDefaultState());
                    } else {
						return false;
					}
                }
            }
        }

		buffer.forEach((pos, block) -> {world.setBlockState(pos, block, Block.NOTIFY_ALL);});
        return true;
    }
}
