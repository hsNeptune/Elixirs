package org.hsneptune.elixirs.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public abstract class AffinityEffect extends StatusEffect {

    private final Identifier resistance;
    private final Identifier weakness;

    public AffinityEffect(StatusEffectCategory category, int color, Identifier resistance, Identifier weakness) {
        super(category, color);
        this.resistance = resistance;
        this.weakness = weakness;
    }

    public Identifier getResistance() {
        return resistance;
    }

    public Identifier getWeakness() {
        return weakness;
    }
}
