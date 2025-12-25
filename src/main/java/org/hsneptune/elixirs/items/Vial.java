package org.hsneptune.elixirs.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class Vial extends Item {


    public Vial(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!stack.getItem().equals(ElixirsItems.VIAL)) {
            return TypedActionResult.pass(stack);
        }

        // Raycast to find what the player is looking at
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = hitResult;
            BlockPos pos = blockHit.getBlockPos();

            // Check if the block contains water
            if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
                // Play sound and replace item
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);


                stack.decrement(1);
                ItemStack filled = new ItemStack(ElixirsItems.WATER_VIAL);

                if (stack.isEmpty()) {
                    return TypedActionResult.success(filled, world.isClient());
                } else {
                    // Otherwise, try to add filled vial to inventory
                    if (!user.getInventory().insertStack(filled)) {
                        user.dropItem(filled, false);
                    }
                    return TypedActionResult.success(stack, world.isClient());
                }
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        BlockState state = world.getBlockState(pos);

        // Check if it's a water cauldron
        if (state.isOf(Blocks.WATER_CAULDRON)) {
            int currentLevel = state.get(LeveledCauldronBlock.LEVEL);

            if (currentLevel == 1) {
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            } else world.setBlockState(pos, state.with(LeveledCauldronBlock.LEVEL, currentLevel-1));

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);


            stack.decrement(1);
            player.giveItemStack(new ItemStack(ElixirsItems.WATER_VIAL));

            return ActionResult.success(world.isClient());
        }

        return ActionResult.PASS;
    }
}
