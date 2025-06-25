package org.hsneptune.elixirs.effects;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.hsneptune.elixirs.items.VialElixirs;

import java.util.ArrayList;
import java.util.List;

import static org.hsneptune.elixirs.items.ElixirsItems.POTENT_DUST;

public class ElixirsEffects {
    public static final RegistryEntry<StatusEffect> RAGE = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("elixirs", "rage"), new RageEffect());
    public static final List<ElixirItemRecipe> CUSTOM_ITEM_RECIPES = new ArrayList<>();
    public static final List<Item> ELIXIRS_INGREDIENTS = new ArrayList<>();

    public static void initialize() {
        ELIXIRS_INGREDIENTS.add(POTENT_DUST);
        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.AWKWARD_VIAL,
                Items.BLAZE_POWDER,
                VialElixirs.RAGE_SERUM_3M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.RAGE_SERUM_3M,
                Items.GLOWSTONE_DUST,
                VialElixirs.RAGE_SERUM_1M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.RAGE_SERUM_3M,
                Items.REDSTONE,
                VialElixirs.RAGE_SERUM_8M
        ));


        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.WATER_VIAL,
                POTENT_DUST,
                ElixirsItems.AWKWARD_VIAL
        ));





        Elixirs.LOGGER.info("Registering Effects");
    }

    public record ElixirItemRecipe(Item from, Item ingredient, Item to) {}
}
