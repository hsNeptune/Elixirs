package org.hsneptune.elixirs.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ExpIncreaseEffect extends StatusEffect {
    public static final Identifier modifierIdentifier = Identifier.of("elixirs", "exp_increase_speed");


    protected ExpIncreaseEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x4fed47);
//        super.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("elixirs", "exp_increase"),
//                0.1f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {

        if (entity instanceof PlayerEntity) {
            if (entity.getWorld().getTime() % 20 == 0) {
                ((PlayerEntity) entity).addExperience(1);
            }
        }

        return true;
    }

    @Override
    public void onApplied(AttributeContainer attributeContainer, int amplifier) {
        super.onApplied(attributeContainer, amplifier);
        EntityAttributeInstance attribute = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (attribute == null) {
            return;
        }
        double value = (Math.atan(0.1*amplifier + 0.25) * (2 / (2*Math.PI)));
        EntityAttributeModifier modifier = new EntityAttributeModifier(modifierIdentifier, value, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        if (!attribute.hasModifier(modifierIdentifier)) {
            attribute.addTemporaryModifier(modifier);
        }
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        EntityAttributeInstance attribute = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(modifierIdentifier);
        }
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {

        return true;
    }

}
