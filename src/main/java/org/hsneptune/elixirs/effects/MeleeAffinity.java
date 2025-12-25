package org.hsneptune.elixirs.effects;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;

public class MeleeAffinity extends AffinityEffect {
    public MeleeAffinity(){
        super(StatusEffectCategory.BENEFICIAL, 0x1a84d3,
                Identifier.ofVanilla("textures/item/diamond_sword.png"),
                Identifier.ofVanilla("textures/item/bow.png"));
    }



    @Override
    public boolean isResistant(DamageSource source) {
        return source.isOf(DamageTypes.MOB_ATTACK) || source.isOf(DamageTypes.MOB_ATTACK_NO_AGGRO) ||
                source.isOf(DamageTypes.PLAYER_ATTACK) || source.isOf(DamageTypes.CACTUS);
    }

    @Override
    public boolean isWeak(DamageSource source) {
        return source.isIn(DamageTypeTags.IS_PROJECTILE);
    }
}
