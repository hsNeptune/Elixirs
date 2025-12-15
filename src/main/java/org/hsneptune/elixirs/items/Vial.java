package org.hsneptune.elixirs.items;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.hsneptune.elixirs.Elixirs;

public class Vial extends Item {


    public Vial(Properties settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        if (!stack.getItem().equals(ElixirsItems.VIAL)) {
            return TypedActionResult.pass(stack);
        }

        // Raycast to find what the player is looking at
        BlockHitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.WATER);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = hitResult;
            BlockPos pos = blockHit.getBlockPos();

            // Check if the block contains water
            if (world.getFluidState(pos).is(FluidTags.WATER)) {
                // Play sound and replace item
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);


                stack.shrink(1);
                ItemStack filled = new ItemStack(ElixirsItems.WATER_VIAL);

                if (stack.isEmpty()) {
                    return TypedActionResult.success(filled, world.isClientSide());
                } else {
                    // Otherwise, try to add filled vial to inventory
                    if (!user.getInventory().add(filled)) {
                        user.drop(filled, false);
                    }
                    return TypedActionResult.success(stack, world.isClientSide());
                }
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        BlockState state = world.getBlockState(pos);

        // Check if it's a water cauldron
        if (state.is(Blocks.WATER_CAULDRON)) {
            int currentLevel = state.getValue(LayeredCauldronBlock.LEVEL);

            if (currentLevel == 1) {
                world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            } else world.setBlockAndUpdate(pos, state.setValue(LayeredCauldronBlock.LEVEL, currentLevel-1));

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);


            stack.shrink(1);
            player.addItem(new ItemStack(ElixirsItems.WATER_VIAL));

            return InteractionResult.success(world.isClientSide());
        }

        return InteractionResult.PASS;
    }
}
