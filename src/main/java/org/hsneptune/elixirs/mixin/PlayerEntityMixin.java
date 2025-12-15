package org.hsneptune.elixirs.mixin;

import net.minecraft.world.entity.player.Player;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @ModifyVariable(method="giveExperiencePoints", at =@At("HEAD"), ordinal = 0, argsOnly = true)
    private int applyIncreasedExperience(int amount) {
        Player self = (Player) (Object) this;
        int newAmount = amount;
        if (self.hasEffect(ElixirsEffects.STARRY)) {
            int amp = self.getEffect(ElixirsEffects.STARRY).getAmplifier();
            newAmount *= (2 + amp);
        }
        return newAmount;

    }
}
