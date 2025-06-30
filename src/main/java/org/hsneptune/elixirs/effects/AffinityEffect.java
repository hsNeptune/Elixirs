package org.hsneptune.elixirs.effects;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.entity.LivingEntity;

public abstract class AffinityEffect extends StatusEffect {

    private final Identifier resistance;
    private final Identifier weakness;


    public AffinityEffect(StatusEffectCategory category, int color, Identifier resistance, Identifier weakness) {
        super(category, color);
        this.resistance = resistance;
        this.weakness = weakness;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
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
