package org.hsneptune.elixirs.effects;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;

public class ProjectileAffinity extends AffinityEffect {

    public ProjectileAffinity() {
        super(MobEffectCategory.BENEFICIAL, 0xB55CE6, Identifier.withDefaultNamespace("textures/item/bow.png"), Identifier.withDefaultNamespace("textures/item/diamond_sword.png"));
    }

    public boolean isResistant(DamageSource source) {
        return source.is(DamageTypeTags.IS_PROJECTILE);
    }

    public boolean isWeak(DamageSource source) {
        return source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO) ||
                source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.CACTUS);
    }

}
