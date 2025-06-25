package org.hsneptune.elixirs.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.hsneptune.elixirs.items.ElixirsItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.hsneptune.elixirs.effects.ElixirsEffects.ELIXIRS_INGREDIENTS;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {

    @Inject(method = "craft", at = @At("HEAD"), cancellable = true)
    private void injectCustomCraft(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {

        Item inputItem = input.getItem();
        Item ingredientItem = ingredient.getItem();

        for (ElixirsEffects.ElixirItemRecipe recipe : ElixirsEffects.CUSTOM_ITEM_RECIPES) {
            if (recipe.from().equals(inputItem.asItem()) && recipe.ingredient().equals(ingredientItem)) {
                cir.setReturnValue(new ItemStack(recipe.to()));
                return;
            }
        }
    }

    @Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
    private void allowCustomIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        System.out.println("custom ingredient list active");
        if (ELIXIRS_INGREDIENTS.contains(stack.getItem())) {
            cir.setReturnValue(true); // don't allow vials in recipes
        }
    }
}
