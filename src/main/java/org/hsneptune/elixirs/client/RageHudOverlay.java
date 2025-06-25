package org.hsneptune.elixirs.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.hsneptune.elixirs.effects.ElixirsEffects;

public class RageHudOverlay {

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player == null) return;

        StatusEffectInstance rage = player.getStatusEffect(ElixirsEffects.RAGE);
        if (rage == null) return;

        int remainingTicks = rage.getDuration();

        Window window = client.getWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();

        int alpha;

        if (remainingTicks < 200) {
            // Flash effect like night vision:
            // Oscillates between 0.0 and 0.25 every ~0.5s
            double phase = (System.currentTimeMillis() % 1000) / 1000.0; // 0.0 - 1.0
            double sine = 0.5 * (1 + Math.sin(phase * 2 * Math.PI)); // Smooth wave
            alpha = (int)(sine * 0x40); // Max alpha = 0x40 (25% opacity)
        } else {
            // Constant red overlay at 25% opacity
            alpha = 0x40;
        }

        int color = (alpha << 24) | 0xFF0000; // ARGB: alpha + red
        context.fill(0, 0, width, height, color);


    }
}