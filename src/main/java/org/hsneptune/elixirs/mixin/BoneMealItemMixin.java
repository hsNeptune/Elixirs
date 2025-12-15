package org.hsneptune.elixirs.mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.tag.ElixirsTags;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.BoneMealItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.util.CaveSurface.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.server.world.ServerWorld;
import java.util.Optional;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.random.Random;


@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {
    @Inject(method = "useOnGround", at = @At("HEAD"), cancellable = true)
    private static void growGlowingFoliage(ItemStack stack, World world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        BlockState state = world.getBlockState(pos);
        BlockState below = world.getBlockState(pos.add(0, -1, 0));

        System.out.println("Code reached checkpoint 0 " + below.toString() + " " + state.toString());
        if (below.isOf(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE)) {
            Random random = Random.create();

            // Pick a random surrounding block position
            BlockPos targetPos = pos.add(
                random.nextInt(3) - 1,
                -1,
                random.nextInt(3) - 1
            );

            System.out.println("Code reached checkpoint 1");
            // Try up to 10 times to find a placeable spot

            for (int i = 0; i < 3; i++) {
                var newPos = targetPos.add(0, i, 0);
                if (world.getBlockState(newPos.add(0, -1, 0)).isReplaceable()) {
					// we'll have to sysprint multiple things out to see which works
                    System.out.println(Registries.BLOCK.iterateEntries(ElixirsTags.GROWS_GLOWING_FOLIAGE).toString());
                    BlockState newState = Registries.BLOCK.getRandomEntry(ElixirsTags.GROWS_GLOWING_FOLIAGE, random).get().value().getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER);
                    if (newState.canPlaceAt(world, targetPos)) {
                        world.setBlockState(targetPos, newState, 3);
                        stack.decrement(1);
                        cir.setReturnValue(true); // cancel original method
                        return;
                    }
                }
            }

                // try another random surrounding block
            targetPos = new BlockPos(
                pos.getX() + random.nextInt(3) - 1,
                pos.getY() + random.nextInt(3) - 1,
                pos.getZ() + random.nextInt(3) - 1
            );
        }
    }
        

}
