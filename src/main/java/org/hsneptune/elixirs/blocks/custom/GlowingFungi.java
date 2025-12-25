package org.hsneptune.elixirs.blocks.custom;


import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import org.hsneptune.elixirs.ElixirsClient;
import org.hsneptune.elixirs.blocks.ElixirsBlocks;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;

public class GlowingFungi extends PlantBlock {
	private static final MapCodec<GlowingFungi> mCodec = createCodec(GlowingFungi::new);
	public static final IntProperty GROWTH = IntProperty.of("growth", 0, 3);
	
	public GlowingFungi(Settings settings) {
		super(settings.noCollision().nonOpaque());
        ElixirsClient.addTransparentBlock(this, RenderLayer.getCutout());  
	}
	
	@Override
	public MapCodec<GlowingFungi> getCodec() {
		return mCodec;
	}


    @Override
    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4,0,4,12,7,12);
    }

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		// TODO Auto-generated method stub
		builder.add(GROWTH);
	}



}
