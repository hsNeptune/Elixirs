package org.hsneptune.elixirs.mixin;

import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.hsneptune.elixirs.items.VialElixirs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.collection.DefaultedList;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {

    @Shadow
    private DefaultedList<ItemStack> inventory;

    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    private void allowCustomVials(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        BrewingStandBlockEntity self = (BrewingStandBlockEntity) (Object) this;
        // Slots 0–2 are bottle slots
        if (slot >= 0 && slot <= 2 && stack.getItem() instanceof VialElixirs) {
            // Only allow insertion if slot is empty (mimics vanilla logic)
            if (self.getStack(slot).isEmpty()) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false); // just in case something tries to double-insert
            }
        }
    }

    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private static void injectCustomCraft(BrewingRecipeRegistry brewingRecipeRegistry, DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack ingredientStack = slots.get(3); // slot 3 is the ingredient slot
        if (ingredientStack.isEmpty()) return;

        for (int i = 0; i < 3; ++i) { // check each bottle slot (0–2)
            ItemStack baseStack = slots.get(i);
            if (baseStack.isEmpty()) continue;

            for (ElixirsEffects.ElixirItemRecipe recipe : ElixirsEffects.CUSTOM_ITEM_RECIPES) {
                if (ElixirsEffects.matchesRecipe(baseStack, ingredientStack, recipe)) {
					cir.cancel();
                    cir.setReturnValue(true);
                    return;
                }
            }
        }

    }

}
