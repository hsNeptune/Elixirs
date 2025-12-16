package org.hsneptune.elixirs.mixin;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(PlayerEntity.class)
public abstract class TurtleMixin extends LivingEntity {


    protected TurtleMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "tick()V", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        byte count = 0;
        byte helmet = 0;
        Iterator<EquipmentSlot> equipmentSlot = AttributeModifierSlot.ARMOR.iterator();
        for (Iterator<EquipmentSlot> it = equipmentSlot; it.hasNext(); ) {
            EquipmentSlot slot = it.next();
            ItemStack stack = this.getEquippedStack(slot);
            if (stack.getItem() == TurtleArmor.TURTLE_CHESTPLATE ||
                    stack.getItem() == TurtleArmor.TURTLE_LEGGINGS ||
                    stack.getItem() == TurtleArmor.TURTLE_BOOTS) {
                count++;
            }

            if (stack.getItem() == Items.TURTLE_HELMET) {
                helmet = 1;
            }
        }

        switch (count) {
            case 1:
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10*20,helmet));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20,1+helmet));
                break;
            case 2:
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10*20,2+helmet));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20,2+helmet));
                break;
            case 3:
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10*20,3));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20,5));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 10*20,1+helmet));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 10*20,0));
                break;

        }
    }

}
