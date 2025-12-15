package org.hsneptune.elixirs.client;

import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.effects.AffinityEffect;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class HudOverlays {

    public static void render(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;

        if (player == null) return;

        MobEffectInstance rage = player.getEffect(ElixirsEffects.RAGE);
        if (rage == null) return;

        int remainingTicks = rage.getDuration();

        Window window = client.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

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

    public static void renderAffinity(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;
        if (player == null) return;

        Window window = client.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

        ArrayList<AffinityEffect> effects = new ArrayList<>();
        for (MobEffectInstance effect : player.getActiveEffects()) {
            if (effect.getEffect().value() instanceof AffinityEffect) {
                effects.add((AffinityEffect) effect.getEffect().value());
            }
        }

        byte counter = 0;
        int row = 0;
        for (AffinityEffect effect : effects) {
            Identifier resistance = effect.getResistance();
            Identifier weakness = effect.getWeakness();

            context.blit(resistance, width/2 - 91 - 29 - 22 - (18 * (counter % 2)), height - 22 - row * 18,
                    50, 0f, 0f, 16, 16, 16, 16);

            context.blit(weakness, width / 2 + 91 + 29 + 4 + (18 * (counter % 2)), height - 22 - row * 18,
                    50, 0f, 0f, 16, 16, 16, 16);
            if (counter++ % 2 == 1) {
                row++;
            }
        }

        row = counter % 2 == 0 ? row - 1 : row;

        if (counter != 0) {
            context.pose().push();
            context.pose().translate(0, 0, 60.0D);
            int y = height - 22 - row * 18 - client.font.lineHeight;
            context.drawString(client.font, Component.translatable("text.elixirs.resistance"),
                    (width/2) - 91 - 29 - 4 - client.font.width(Component.translatable("text.elixirs.resistance")),
                    y, 0xFFC434, false);
            context.drawString(client.font, Component.translatable("text.elixirs.weakness"),
                    (width/2) + 91 + 29 + 4,
                    y, 0xDFDFFF, false);
            context.pose().pop();

        }


    }
}
