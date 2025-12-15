package org.hsneptune.elixirs.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.hsneptune.elixirs.items.VialElixirs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandBlockEntity.class)
public abstract class BrewingStandBlockEntityMixin {

    @Shadow
    private NonNullList<ItemStack> items;

    @Inject(method = "canPlaceItem", at = @At("HEAD"), cancellable = true)
    private void allowCustomVials(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        BrewingStandBlockEntity self = (BrewingStandBlockEntity) (Object) this;
        // Slots 0–2 are bottle slots
        if (slot >= 0 && slot <= 2 && stack.getItem() instanceof VialElixirs) {
            // Only allow insertion if slot is empty (mimics vanilla logic)
            if (self.getItem(slot).isEmpty()) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false); // just in case something tries to double-insert
            }
        }
    }

    @Inject(method = "isBrewable", at = @At("HEAD"), cancellable = true)
    private static void injectCustomCraft(PotionBrewing brewingRecipeRegistry, NonNullList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack ingredientStack = slots.get(3); // slot 3 is the ingredient slot
        if (ingredientStack.isEmpty()) return;

        Item ingredientItem = ingredientStack.getItem();

        for (int i = 0; i < 3; ++i) { // check each bottle slot (0–2)
            ItemStack baseStack = slots.get(i);
            if (baseStack.isEmpty()) continue;

            Item baseItem = baseStack.getItem();

            for (ElixirsEffects.ElixirItemRecipe recipe : ElixirsEffects.CUSTOM_ITEM_RECIPES) {

                if (recipe.from().equals(baseItem) && recipe.ingredient().equals(ingredientItem)) {

                    cir.cancel();
                    cir.setReturnValue(true);
                    return;
                }
            }
        }

    }
}