package org.hsneptune.elixirs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import org.hsneptune.elixirs.blocks.*;
import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;

import net.minecraft.block.*;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGenMixin {
    
    @Inject(method = "generateFeatures", at = @At("RETURN"))
    private void onGenerateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci) {
        try {
        ChunkPos chunkPos = chunk.getPos();
        int minY = world.getBottomY();
        int maxY = minY + world.getHeight();

        Block foliage = ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE;

        // Loop through the entire chunk section
        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                int x = chunkPos.getStartX() + dx;
                int z = chunkPos.getStartZ() + dz;

                for (int y = minY; y < maxY; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = world.getBlockState(pos);

                    // Get the biome at this block position
                    RegistryEntry<Biome> biome = world.getBiome(pos);

                    if (biome.matchesKey(ElixirsBiomes.GLOWING_MUSHROOM_CAVES)) {
                        if (state.isOf(Blocks.STONE) || state.isOf(Blocks.DEEPSLATE)) {
                            world.setBlockState(pos, foliage.getDefaultState(), Block.FORCE_STATE);
                        }
                    }
                }
            }
        }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
