package org.hsneptune.elixirs.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class RageEffect extends StatusEffect {
    protected RageEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xDF0000);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        // This method is called every tick the effect is active.
        // We'll handle the damage multiplication in event listeners, not here.
        // However, you could put other tick-based effects here if needed.
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Return true to allow applyUpdateEffect to be called every tick.
        return true;
    }
}
