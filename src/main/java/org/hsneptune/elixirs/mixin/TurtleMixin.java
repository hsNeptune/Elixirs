package org.hsneptune.elixirs.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class TurtleMixin extends LivingEntity {


    protected TurtleMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        byte count = 0;
        byte helmet = 0;
        for (ItemStack stack: this.getArmorItems()) {
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
                this.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 10*20,helmet));
                this.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 10*20,1+helmet));
                break;
            case 2:
                this.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 10*20,2+helmet));
                this.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 10*20,2+helmet));
                break;
            case 3:
                this.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 10*20,3));
                this.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 10*20,5));
                this.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 10*20,1+helmet));
                this.addEffect(new MobEffectInstance(MobEffects.LUCK, 10*20,0));
                break;

        }
    }

}
