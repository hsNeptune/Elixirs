package org.hsneptune.elixirs.effects;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;
import org.hsneptune.elixirs.items.VialElixirs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.hsneptune.elixirs.items.ElixirsItems.POTENT_DUST;

public class ElixirsEffects {
    public static final RegistryEntry<StatusEffect> RAGE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "rage"), new RageEffect());
    public static final RegistryEntry<StatusEffect> STARRY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "starry"),
            new ExpIncreaseEffect());
    public static final RegistryEntry<StatusEffect> MELEE_AFFINITY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "melee_affinity"), new MeleeAffinity());
    public static final RegistryEntry<StatusEffect> PROJECTILE_AFFINITY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "projectile_affinity"), new ProjectileAffinity());

    public static final RegistryEntry<StatusEffect> HEAD_AFFINITY = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "head_affinity"), new HeadAffinity());

    public static final List<ElixirItemRecipe> CUSTOM_ITEM_RECIPES = new ArrayList<>();
    public static final List<Item> ELIXIRS_INGREDIENTS = new ArrayList<>();

	private static ItemStack potionSingleEffect(Item item, int duration, int amplifier) {
		ItemStack stack = new ItemStack(item);
		stack.set(VialElixirs.DURATION, List.of(duration));
		stack.set(VialElixirs.AMPLIFIER, List.of(amplifier));
		return stack;
	}
    public static void initialize() {
//        ELIXIRS_INGREDIENTS.add(POTENT_DUST);
	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.AWKWARD_VIAL),
			new ItemStack(Items.BLAZE_POWDER),
			potionSingleEffect(VialElixirs.RAGE_SERUM, 1200 * 3, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.AWKWARD_VIAL),
			new ItemStack(Items.BONE),
			new ItemStack(ElixirsItems.DAMAGE_AFFINITY)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.DAMAGE_AFFINITY),
			new ItemStack(Items.WOODEN_SWORD),
			potionSingleEffect(VialElixirs.MELEE_AFFINITY_SERUM, (int)(1200 * 0.5), 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.MELEE_AFFINITY_SERUM, (int)(1200 * 0.5), 0),
			new ItemStack(Items.REDSTONE),
			potionSingleEffect(VialElixirs.MELEE_AFFINITY_SERUM, 1200, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.DAMAGE_AFFINITY),
			new ItemStack(Items.BOW),
			potionSingleEffect(VialElixirs.PROJECTILE_AFFINITY_SERUM, (int)(1200 * 0.5), 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.PROJECTILE_AFFINITY_SERUM, (int)(1200 * 0.5), 0),
			new ItemStack(Items.REDSTONE),
			potionSingleEffect(VialElixirs.PROJECTILE_AFFINITY_SERUM, 1200, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.DAMAGE_AFFINITY),
			new ItemStack(Items.HEAVY_CORE),
			potionSingleEffect(VialElixirs.HEAD_AFFINITY_SERUM, (int)(1200 * 0.5), 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.HEAD_AFFINITY_SERUM, (int)(1200 * 0.5), 0),
			new ItemStack(Items.REDSTONE),
			potionSingleEffect(VialElixirs.HEAD_AFFINITY_SERUM, 1200, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.AWKWARD_VIAL),
			new ItemStack(Items.EXPERIENCE_BOTTLE),
			potionSingleEffect(VialElixirs.STARRY_SERUM, 1200 * 3, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.STARRY_SERUM, 1200 * 3, 0),
			new ItemStack(Items.GLOWSTONE_DUST),
			potionSingleEffect(VialElixirs.STARRY_SERUM, (int)(1200 * 1.5), 1)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.STARRY_SERUM, 1200 * 3, 0),
			new ItemStack(Items.REDSTONE),
			potionSingleEffect(VialElixirs.STARRY_SERUM, 1200 * 8, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.STARRY_SERUM, (int)(1200 * 1.5), 1),
			new ItemStack(POTENT_DUST),
			potionSingleEffect(VialElixirs.STARRY_SERUM, (int)(1200 * 0.5), 2)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.RAGE_SERUM, 1200 * 3, 0),
			new ItemStack(Items.GLOWSTONE_DUST),
			potionSingleEffect(VialElixirs.RAGE_SERUM, (int)(1200 * 1.5), 1)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			potionSingleEffect(VialElixirs.RAGE_SERUM, 1200 * 3, 0),
			new ItemStack(Items.REDSTONE),
			potionSingleEffect(VialElixirs.RAGE_SERUM, 1200 * 8, 0)
	));

	CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
			new ItemStack(ElixirsItems.WATER_VIAL),
			new ItemStack(POTENT_DUST),
			new ItemStack(ElixirsItems.AWKWARD_VIAL)
	));




        Elixirs.LOGGER.info("Registering Effects");
    }


	public static boolean matchesRecipe(ItemStack base, ItemStack ingredient, ElixirsEffects.ElixirItemRecipe recipe) {
		if (base.isEmpty() || ingredient.isEmpty()) return false;

		// Compare items first
		if (base.getItem() != recipe.from().getItem()) return false;
		if (ingredient.getItem() != recipe.ingredient().getItem()) return false;

		// Compare component lists
		List<Integer> baseDur = base.getOrDefault(VialElixirs.DURATION, null);
		List<Integer> baseAmp = base.getOrDefault(VialElixirs.AMPLIFIER, null);
		List<Integer> recipeDur = recipe.from().getOrDefault(VialElixirs.DURATION, null);
		List<Integer> recipeAmp = recipe.from().getOrDefault(VialElixirs.AMPLIFIER, null);

        return baseDur == recipeDur && baseAmp == recipeAmp;
		// Exact match (same number of effects, same values)
	}
    public record ElixirItemRecipe(ItemStack from, ItemStack ingredient, ItemStack to) {
        public ElixirItemRecipe (ItemStack from, ItemStack ingredient, ItemStack to) {
            this.from = from;
            this.ingredient = ingredient;
            this.to = to;
            FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                builder.registerItemRecipe(
                        // Input potion.
                        from.getItem(),
                        // Ingredient
                        ingredient.getItem(),
                        // Output potion.
                        to.getItem()
                );
            });
        }
    }
}
