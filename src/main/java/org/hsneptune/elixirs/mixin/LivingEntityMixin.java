package org.hsneptune.elixirs.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

import org.hsneptune.elixirs.effects.AffinityEffect;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

	@Shadow public abstract float getDamageBlockedAmount(ServerWorld world, DamageSource source, float amount);

    @ModifyVariable(
            method = "damage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyRageDamageMultiplier(ServerWorld world, float amount, DamageSource source) {
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

        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (StatusEffectInstance effect : self.getStatusEffects()) {
            if (effect.getEffectType().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffectType().value());
            }
        }

        for (AffinityEffect effect : effects) {
            if (effect.isResistant(source) && !effect.isWeak(source)) {
                modifiedAmount = 0;
            }

            if (effect.isWeak(source)) {
                modifiedAmount = amount;
                break;
            }
        }

        return modifiedAmount;
    }

    @Inject(method = "damage", at = @At(value ="INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isDead()Z", shift = At.Shift.AFTER))
    private void applyDamages(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

		float g = this.getDamageBlockedAmount(world, source, amount);
        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (StatusEffectInstance effect : self.getStatusEffects()) {
            if (effect.getEffectType().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffectType().value());
            }
        }
        for (AffinityEffect effect : effects) {
            if (effect.isWeak(source) && g <= 0.0f) {
                self.setHealth(0.0f);
            }
        }

    }

}
