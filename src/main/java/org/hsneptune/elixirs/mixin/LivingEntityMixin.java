package org.hsneptune.elixirs.mixin;

import org.hsneptune.elixirs.Elixirs;
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
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);

    @Shadow public abstract boolean blockedByShield(DamageSource source);

    @ModifyVariable(
            method = "damage(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyRageDamageMultiplier(float amount, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;

        float modifiedAmount = amount;

        if (self.hasEffect(ElixirsEffects.RAGE)) {
            int amp = self.getEffect(ElixirsEffects.RAGE).getAmplifier();
            modifiedAmount *= (amp + 2);
        }

        if (source.getEntity() instanceof LivingEntity attacker) {
            if (attacker.hasEffect(ElixirsEffects.RAGE)) {
                int amp = attacker.getEffect(ElixirsEffects.RAGE).getAmplifier();
                modifiedAmount *= (amp + 2);
            }
        }

        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (MobEffectInstance effect : self.getActiveEffects()) {
            if (effect.getEffect().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffect().value());
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

    @Inject(method = "hurtServer", at = @At(value ="INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDeadOrDying()Z", shift = At.Shift.AFTER))
    private void applyDamages(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (MobEffectInstance effect : self.getActiveEffects()) {
            if (effect.getEffect().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffect().value());
            }
        }
        for (AffinityEffect effect : effects) {
            if (effect.isWeak(source) && !self.blockedByShield(source)) {
                self.setHealth(0.0f);
            }
        }

    }

}
