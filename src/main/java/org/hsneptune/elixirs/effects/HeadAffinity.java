package org.hsneptune.elixirs.effects;


import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;

public class HeadAffinity extends AffinityEffect {

    public HeadAffinity() {
        super(StatusEffectCategory.BENEFICIAL, 0x14C14E, Identifier.ofVanilla("textures/item/mace.png"), Identifier.ofVanilla("textures/mob_effect/slow_falling.png")); 
    }

    @Override
    public boolean isResistant(DamageSource source) {
        return source.isIn(DamageTypeTags.DAMAGES_HELMET);
    }

    @Override
    public boolean isWeak(DamageSource source) {
        return source.isIn(DamageTypeTags.IS_FALL);
    }
}
