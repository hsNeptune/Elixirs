package org.hsneptune.elixirs.effects;

import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class AffinityEffect extends MobEffect {

    private final Identifier resistance;
    private final Identifier weakness;


    public AffinityEffect(MobEffectCategory category, int color, Identifier resistance, Identifier weakness) {
        super(category, color);
        this.resistance = resistance;
        this.weakness = weakness;
    }

    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }

    public abstract boolean isResistant(DamageSource source);
    public abstract boolean isWeak(DamageSource source);

    public Identifier getResistance() {
        return resistance;
    }

    public Identifier getWeakness() {
        return weakness;
    }
}
