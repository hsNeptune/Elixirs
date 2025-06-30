package org.hsneptune.elixirs.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.effects.AffinityEffect;
import org.hsneptune.elixirs.effects.ElixirsEffects;

import java.util.ArrayList;

public class HudOverlays {

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

    public static void renderAffinity(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null) return;

        Window window = client.getWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();

        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (StatusEffectInstance effect : player.getStatusEffects()) {
            if (effect.getEffectType().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffectType().value());
            }
        }

        byte counter = 0;
        int row = 0;
        for (AffinityEffect effect : effects) {
            Identifier resistance = effect.getResistance();
            Identifier weakness = effect.getWeakness();

            context.drawTexture(resistance, width/2 - 91 - 29 - 22 - (18 * (counter % 2)), height - 22 - row * 18,
                    50, 0f, 0f, 16, 16, 16, 16);

            context.drawTexture(weakness, width / 2 + 91 + 29 + 4 + (18 * (counter % 2)), height - 22 - row * 18,
                    50, 0f, 0f, 16, 16, 16, 16);
            if (counter++ % 2 == 1) {
                row++;
            }
        }

        row = counter % 2 == 0 ? row - 1 : row;

        if (counter != 0) {
            context.getMatrices().push();
            context.getMatrices().translate(0, 0, 60.0D);
            int y = height - 22 - row * 18 - client.textRenderer.fontHeight;
            context.drawText(client.textRenderer, Text.translatable("text.elixirs.resistance"),
                    (width/2) - 91 - 29 - 4 - client.textRenderer.getWidth(Text.translatable("text.elixirs.resistance")),
                    y, 0xFFC434, false);
            context.drawText(client.textRenderer, Text.translatable("text.elixirs.weakness"),
                    (width/2) + 91 + 29 + 4,
                    y, 0xDFDFFF, false);
            context.getMatrices().pop();

        }


    }
}
