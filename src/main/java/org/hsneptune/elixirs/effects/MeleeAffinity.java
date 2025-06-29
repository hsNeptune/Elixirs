package org.hsneptune.elixirs.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class MeleeAffinity extends AffinityEffect {
    public MeleeAffinity(){
        super(StatusEffectCategory.BENEFICIAL, 0x3a3ebc,
                Identifier.ofVanilla("textures/item/diamond_sword.png"),
                Identifier.ofVanilla("textures/item/bow.png"));
    }


    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return true;
    }

}
