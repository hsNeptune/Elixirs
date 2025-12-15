package org.hsneptune.elixirs.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractClientPlayer.class)
public class RageFovMixin {
    @ModifyVariable(
            method = "getFovMultiplier()F",
            at = @At("STORE"),
            ordinal = 0
    )
    public float fovMultiplier(float f) {
        if (Minecraft.getInstance().player.hasEffect(ElixirsEffects.RAGE)) {
            return (float) (f * (1 + (.05f * Math.sqrt(Minecraft.getInstance().player.getEffect(ElixirsEffects.RAGE).getAmplifier()))));
        } else {
            return f;
        }

    }
}
