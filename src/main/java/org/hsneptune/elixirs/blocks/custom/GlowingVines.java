package org.hsneptune.elixirs.blocks.custom;

import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import org.hsneptune.elixirs.ElixirsClient;
import org.hsneptune.elixirs.blocks.ElixirsBlocks;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.BlockState;

public class GlowingVines extends TallPlantBlock {
    private static final MapCodec<GlowingVines> CODEC = createCodec(GlowingVines::new);

    public GlowingVines(Settings settings) {
        super(settings.noCollision().nonOpaque());
        ElixirsClient.addTransparentBlock(this, RenderLayer.getCutout());  
    }
    
    @Override 
    public MapCodec<GlowingVines> getCodec() {
       return CODEC; 
    }

    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state, world, pos, context);
    }

}
