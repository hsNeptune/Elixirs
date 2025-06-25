package org.hsneptune.elixirs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractClientPlayerEntity.class)
public class RageFovMixin {
    @ModifyVariable(
            method = "getFovMultiplier()F",
            at = @At("STORE"),
            ordinal = 0
    )
    public float fovMultiplier(float f) {
        if (MinecraftClient.getInstance().player.hasStatusEffect(ElixirsEffects.RAGE)) {
            return (float) (f * (1 + (.05f * Math.sqrt(MinecraftClient.getInstance().player.getStatusEffect(ElixirsEffects.RAGE).getAmplifier()))));
        } else {
            return f;
        }

    }
}
