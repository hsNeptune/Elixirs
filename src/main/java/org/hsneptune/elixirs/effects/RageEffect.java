package org.hsneptune.elixirs.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RageEffect extends MobEffect {
    protected RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xDF0000);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        // This method is called every tick the effect is active.
        // We'll handle the damage multiplication in event listeners, not here.
        // However, you could put other tick-based effects here if needed.
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Return true to allow applyUpdateEffect to be called every tick.
        return true;
    }
}
