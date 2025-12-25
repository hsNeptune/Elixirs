package org.hsneptune.elixirs.effects;

import java.time.Instant;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class TemporalDissonance extends StatusEffect {
	public static final Identifier slownessIdentifier = Identifier.of("elixirs", "temporal_dissonance_slow"); 
	public static final Identifier attackSlowIdentifier = Identifier.of("elixirs", "temporal_dissonance_attack"); 
	private long startTime;

	protected TemporalDissonance() {
		super(StatusEffectCategory.HARMFUL, 0x3f1535);
	}


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		// TODO add condition disabling the effect if they have ceratin armor on
		var random = entity.getWorld().getRandom();
	    int baseInterval = 120;

    // Reduce interval by amplifier (higher amplifier = more frequent)
		int interval = Math.max(10, baseInterval - (amplifier * 20)); 	
		if (entity.getWorld().getTime() % interval == 0 && random.nextDouble() > 0.05D) {
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();

			// Pick random offsets in [-2, +2] range for X, Y, Z (5x5x5 cube)
			double offsetX = random.nextInt(7) - 3;
			double offsetY = random.nextInt(7);
			double offsetZ = random.nextInt(7) - 3;
			entity.requestTeleport(x + offsetX, y + offsetY, z + offsetZ);
			ParticleUtil.spawnParticlesAround(entity.getWorld(), 
					new BlockPos((int) (x + offsetX), (int) (y + offsetY), (int) (z + offsetZ)),
					7*3,
					3.0,
					1.0,
					false,
					ParticleTypes.ELECTRIC_SPARK
					);
		}


        return true;
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {

        return true;
    }

	@Override
	public void onApplied(AttributeContainer attributeContainer, int amplifier) {
		super.onApplied(attributeContainer, amplifier);

		// --- Movement speed debuff ---
		EntityAttributeInstance moveSpeed = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (moveSpeed != null) {
			double moveValue = -0.5D;
			EntityAttributeModifier moveModifier = new EntityAttributeModifier(slownessIdentifier, moveValue, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			if (!moveSpeed.hasModifier(slownessIdentifier)) {
				moveSpeed.addTemporaryModifier(moveModifier);
			}
		}

		// --- Attack speed debuff ---
		EntityAttributeInstance attackSpeed = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		if (attackSpeed != null) {
			double attackValue = -0.9D; // 90% reduction
			EntityAttributeModifier attackModifier = new EntityAttributeModifier(attackSlowIdentifier, attackValue, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
			if (!attackSpeed.hasModifier(attackSlowIdentifier)) {
				attackSpeed.addTemporaryModifier(attackModifier);
			}
		}
	}

	@Override
	public void onRemoved(AttributeContainer attributeContainer) {
		super.onRemoved(attributeContainer);

		// Remove movement speed modifier
		EntityAttributeInstance moveSpeed = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (moveSpeed != null) {
			moveSpeed.removeModifier(slownessIdentifier);
		}

		// Remove attack speed modifier
		EntityAttributeInstance attackSpeed = attributeContainer.getCustomInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		if (attackSpeed != null) {
			attackSpeed.removeModifier(attackSlowIdentifier);
		}
	}
}
