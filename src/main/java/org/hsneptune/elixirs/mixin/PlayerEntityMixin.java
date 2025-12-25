package org.hsneptune.elixirs.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @ModifyVariable(method="addExperience", at =@At("HEAD"), ordinal = 0, argsOnly = true)
    private int applyIncreasedExperience(int amount) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        int newAmount = amount;
        if (self.hasStatusEffect(ElixirsEffects.STARRY)) {
            int amp = self.getStatusEffect(ElixirsEffects.STARRY).getAmplifier();
            newAmount *= (2 + amp);
        }
        return newAmount;

    }
}
