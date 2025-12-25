package org.hsneptune.elixirs.mixin;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.Optional;

import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.blocks.custom.GlowingFungi;
import org.hsneptune.elixirs.tag.ElixirsTags;
import org.hsneptune.elixirs.worldgen.feature.ElixirsFeatures;
import org.hsneptune.elixirs.worldgen.feature.GlowingMushroomFeature;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.BoneMealItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;


@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin {

    @Inject(method = "useOnGround", at = @At("HEAD"), cancellable = true)
    private static void growGlowingFoliage(ItemStack stack, World world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        if (!(world instanceof ServerWorld)) return;

        // Must be used on glowing mushroom foliage
        if (!world.getBlockState(pos.down()).isOf(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE)) {
            return;
        }

        Random random = world.getRandom();

        int placed = 0;
        int maxPlacements = 5;
        int safetyLimit = 60; // prevents infinite loops
        int attempts = 0;

        while (placed < maxPlacements && attempts < safetyLimit) {
            attempts++;

            // Random position in 5x5x5 area
            BlockPos basePos = pos.add(
                    random.nextInt(5) - 2,
                    random.nextInt(5),
                    random.nextInt(5) - 2
            );

            BlockPos groundPos = basePos.down();

            // Must grow on glowing mushroom foliage
            if (!world.getBlockState(groundPos).isOf(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE)) {
                continue;
            }

            // Base block must be air
            if (!world.isAir(basePos)) continue;

            // Pick random foliage
            BlockState plantState = Registries.BLOCK
                    .getRandomEntry(ElixirsTags.GROWS_GLOWING_FOLIAGE, random)
                    .orElse(null)
                    .value()
                    .getDefaultState();

            Block plantBlock = plantState.getBlock();

            // ===== TALL PLANTS =====
            if (plantBlock instanceof TallPlantBlock) {
                BlockPos upperPos = basePos.up();

                if (!world.isAir(upperPos)) continue;

                world.setBlockState(
                        basePos,
                        plantState.with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER),
                        3
                );
                world.setBlockState(
                        upperPos,
                        plantState.with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER),
                        3
                );

                placed++;
            }
            // ===== SINGLE BLOCK PLANTS =====
            else {
                world.setBlockState(basePos, plantState, 3);
                placed++;
            }
        }

        if (placed > 0) {
            stack.decrement(1);
            cir.setReturnValue(true);
        }
    }
	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true, order=2000)
	private static void growBigGlowingMushroom(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {

		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		ItemStack stack = context.getStack();
		if (!(world instanceof ServerWorld serverWorld)) return;
		

		BlockState state = world.getBlockState(pos);
		System.out.println(state.getBlock().toString());
		// Only trigger if clicking on your small glowing mushroom
		if (!state.isOf(ElixirsBlocks.GLOWING_MUSHROOM)) return;

		Random random = world.getRandom();
		int growth = state.get(GlowingFungi.GROWTH);
		
		if (growth > 2 && random.nextFloat() > 0.75F) {
			
			world.setBlockState(pos, state.with(GlowingFungi.GROWTH, growth + 1), 3);
			ParticleUtil.spawnParticlesAround(world, pos, 2 * 3, 3.0, 1.0, false, ParticleTypes.HAPPY_VILLAGER);
			return ;
		} else {
			ParticleUtil.spawnParticlesAround(world, pos, 2 * 3, 3.0, 1.0, false, ParticleTypes.HAPPY_VILLAGER);
		}

		// Use your configured big mushroom feature
		Feature<DefaultFeatureConfig> bigMushroomFeature = ElixirsFeatures.BIG_MUSHROOM_FEATURE;

		System.out.println("Bone Meal used on GM at " + pos.toString());
		// Try generating the big mushroom
		if (bigMushroomFeature.generateIfValid(DefaultFeatureConfig.DEFAULT, serverWorld, serverWorld.getChunkManager().getChunkGenerator(), random, pos)) {
			System.out.println("Generation valid");
			// Successfully grew big mushroom, consume bone meal
			stack.decrement(1);
			cir.setReturnValue(ActionResult.SUCCESS); // Cancel original bone meal behavior
		}


		// else fail silently, allow normal bone meal behavior
	}

}
