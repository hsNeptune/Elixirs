package org.hsneptune.elixirs.effects;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;

public class ProjectileAffinity extends AffinityEffect {

    public ProjectileAffinity() {
        super(StatusEffectCategory.BENEFICIAL, 0xB55CE6, Identifier.ofVanilla("textures/item/bow.png"), Identifier.ofVanilla("textures/item/diamond_sword.png"));
    }

    public boolean isResistant(DamageSource source) {
        return source.isIn(DamageTypeTags.IS_PROJECTILE);
    }

    public boolean isWeak(DamageSource source) {
        return source.isOf(DamageTypes.MOB_ATTACK) || source.isOf(DamageTypes.MOB_ATTACK_NO_AGGRO) ||
                source.isOf(DamageTypes.PLAYER_ATTACK) || source.isOf(DamageTypes.CACTUS);
    }

}
