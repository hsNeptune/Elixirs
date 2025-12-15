package org.hsneptune.elixirs.effects;


import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;

public class HeadAffinity extends AffinityEffect {

    public HeadAffinity() {
        super(MobEffectCategory.BENEFICIAL, 0xACB9C2, Identifier.withDefaultNamespace("textures/item/mace.png"), Identifier.withDefaultNamespace("textures/mob_effect/slow_falling.png")); 
    }

    @Override
    public boolean isResistant(DamageSource source) {
        return source.is(DamageTypeTags.DAMAGES_HELMET);
    }

    @Override
    public boolean isWeak(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL);
    }
}
