package org.hsneptune.elixirs.effects;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;
import org.hsneptune.elixirs.items.VialElixirs;

import java.util.ArrayList;
import java.util.List;

import static org.hsneptune.elixirs.items.ElixirsItems.POTENT_DUST;

public class ElixirsEffects {
    public static final Holder<MobEffect> RAGE = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath("elixirs", "rage"), new RageEffect());
    public static final Holder<MobEffect> STARRY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath("elixirs", "starry"),
            new ExpIncreaseEffect());
    public static final Holder<MobEffect> MELEE_AFFINITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath("elixirs", "melee_affinity"), new MeleeAffinity());
    public static final Holder<MobEffect> PROJECTILE_AFFINITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath("elixirs", "projectile_affinity"), new ProjectileAffinity());

    public static final Holder<MobEffect> HEAD_AFFINITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath("elixirs", "head_affinity"), new HeadAffinity());

    public static final List<ElixirItemRecipe> CUSTOM_ITEM_RECIPES = new ArrayList<>();
    public static final List<Item> ELIXIRS_INGREDIENTS = new ArrayList<>();

    public static void initialize() {
//        ELIXIRS_INGREDIENTS.add(POTENT_DUST);
        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.AWKWARD_VIAL,
                Items.BLAZE_POWDER,
                VialElixirs.RAGE_SERUM_3M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.AWKWARD_VIAL,
                Items.BONE,
                ElixirsItems.DAMAGE_AFFINITY
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.DAMAGE_AFFINITY,
                Items.WOODEN_SWORD,
                VialElixirs.MELEE_AFFINITY_SERUM_30S
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.MELEE_AFFINITY_SERUM_30S,
                Items.REDSTONE,
                VialElixirs.MELEE_AFFINITY_SERUM_1M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.DAMAGE_AFFINITY,
                Items.BOW,
                VialElixirs.PROJECTILE_AFFINITY_SERUM_30S
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.PROJECTILE_AFFINITY_SERUM_30S,
                Items.REDSTONE,
                VialElixirs.PROJECTILE_AFFINITY_SERUM_1M
        ));
        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.DAMAGE_AFFINITY,
                Items.HEAVY_CORE,
                VialElixirs.HEAD_AFFINITY_SERUM_30S
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.HEAD_AFFINITY_SERUM_30S,
                Items.REDSTONE,
                VialElixirs.HEAD_AFFINITY_SERUM_1M
        ));
        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                ElixirsItems.AWKWARD_VIAL,
                Items.EXPERIENCE_BOTTLE,
                VialElixirs.STARRY_SERUM_3M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.STARRY_SERUM_3M,
                Items.GLOWSTONE_DUST,
                VialElixirs.STARRY_SERUM_EFFECTIVE
        ));


        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                VialElixirs.STARRY_SERUM_3M,
                Items.REDSTONE,
                VialElixirs.STARRY_SERUM_8M
        ));

        CUSTOM_ITEM_RECIPES.add(new ElixirItemRecipe(
                    VialElixirs.STARRY_SERUM_EFFECTIVE,
                    POTENT_DUST,
                    VialElixirs.STARRY_SERUM_SUPER_EFFECTIVE
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

    public record ElixirItemRecipe(Item from, Item ingredient, Item to) {
        public ElixirItemRecipe (Item from, Item ingredient, Item to) {
            this.from = from;
            this.ingredient = ingredient;
            this.to = to;
            FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
                builder.addContainerRecipe(
                        // Input potion.
                        from,
                        // Ingredient
                        ingredient,
                        // Output potion.
                        to
                );
            });
        }
    }
}
