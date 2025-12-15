package org.hsneptune.elixirs.effects;

import net.minecraft.entity.attribute.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class ExpIncreaseEffect extends MobEffect {
    public static final Identifier modifierIdentifier = Identifier.fromNamespaceAndPath("elixirs", "exp_increase_speed");


    protected ExpIncreaseEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x4fed47);
//        super.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("elixirs", "exp_increase"),
//                0.1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof Player) {
            if (entity.getWorld().getTime() % 20 == 0) {
                ((Player) entity).giveExperiencePoints(1);
            }
        }

        return true;
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeContainer, int amplifier) {
        super.addAttributeModifiers(attributeContainer, amplifier);
        AttributeInstance attribute = attributeContainer.getInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (attribute == null) {
            return;
        }
        double value = (Math.atan(0.1*amplifier + 0.25) * (2 / (2*Math.PI)));
        AttributeModifier modifier = new AttributeModifier(modifierIdentifier, value, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        if (!attribute.hasModifier(modifierIdentifier)) {
            attribute.addTransientModifier(modifier);
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeContainer) {
        super.removeAttributeModifiers(attributeContainer);
        AttributeInstance attribute = attributeContainer.getInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(modifierIdentifier);
        }
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {

        return true;
    }

}
