package org.hsneptune.elixirs.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyRageDamageMultiplier(float amount, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;

        float modifiedAmount = amount;

        if (self.hasStatusEffect(ElixirsEffects.RAGE)) {
            int amp = self.getStatusEffect(ElixirsEffects.RAGE).getAmplifier();
            modifiedAmount *= (amp + 2);
        }

        if (source.getAttacker() instanceof LivingEntity attacker) {
            if (attacker.hasStatusEffect(ElixirsEffects.RAGE)) {
                int amp = attacker.getStatusEffect(ElixirsEffects.RAGE).getAmplifier();
                modifiedAmount *= (amp + 2);
            }
        }

        return modifiedAmount;
    }




}
