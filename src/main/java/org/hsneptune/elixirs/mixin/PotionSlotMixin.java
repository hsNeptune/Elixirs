package org.hsneptune.elixirs.mixin;

import net.minecraft.world.item.ItemStack;
import org.hsneptune.elixirs.items.VialElixirs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
public abstract class PotionSlotMixin {
    @Inject(method = "mayPlaceItem", at = @At("HEAD"), cancellable = true)
    private static void allowCustomVialInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof VialElixirs) {
            cir.setReturnValue(true); // allow vial in GUI
        }
    }


}