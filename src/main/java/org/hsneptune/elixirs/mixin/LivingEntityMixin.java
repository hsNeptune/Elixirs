package org.hsneptune.elixirs.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow public abstract boolean blockedByShield(DamageSource source);

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

        if ((source.isOf(DamageTypes.MOB_ATTACK) ||
                source.isOf(DamageTypes.MOB_ATTACK_NO_AGGRO) ||
                source.isOf(DamageTypes.PLAYER_ATTACK) ||
                source.isOf(DamageTypes.CACTUS)) && !self.blockedByShield(source) && self.hasStatusEffect(ElixirsEffects.MELEE_AFFINITY)) {
            modifiedAmount = 0;
        }

        return modifiedAmount;
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void applyDamages(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (source.isIn(DamageTypeTags.IS_PROJECTILE) && !self.blockedByShield(source) && self.hasStatusEffect(ElixirsEffects.MELEE_AFFINITY)) {
            self.setHealth(0.0f);
        }


    }




}
