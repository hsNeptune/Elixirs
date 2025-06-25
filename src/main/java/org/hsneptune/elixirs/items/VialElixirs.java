package org.hsneptune.elixirs.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class VialElixirs extends PotionItem {
    private final List<RegistryEntry<StatusEffect>> effects = new ArrayList<>();
    private final List<Integer> durations = new ArrayList<>();
    private final List<Integer> amplifiers = new ArrayList<>();
    private final List<Formatting> colors = new ArrayList<>();
    private final boolean isEffect;
    private final String id;


    public VialElixirs(Settings settings, boolean isEffect, String id) {

        super(settings);

        this.isEffect = isEffect;
        this.id = id;
    }



    public VialElixirs addEffect(RegistryEntry<StatusEffect> effect, int durationTicks, int amplifier, Formatting color) {
        effects.add(effect);
        durations.add(durationTicks);
        amplifiers.add(amplifier);
        colors.add(color);
        return this;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        for (int i = 0; i < effects.size(); i++) {
            RegistryEntry<StatusEffect> effect = effects.get(i);
            int duration = durations.get(i);
            int amplifier = amplifiers.get(i);
            user.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        }


        return new ItemStack(ElixirsItems.VIAL);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (isEffect) {


            for (int i = 0; i < effects.size(); i++) {
                StatusEffect effect = effects.get(i).value();
                int amplifier = amplifiers.get(i);
                int durationTicks = durations.get(i);
                Formatting color = colors.get(i);

                String effectName = Text.translatable(effect.getTranslationKey()).getString();
                String ampText = toRomanNumeral(amplifier + 1);
                String durationText = formatDuration(durationTicks);

                tooltip.add(Text.literal(effectName + " " + ampText + " (" + durationText + ")").formatted(color));
            }
        } else {
            tooltip.add(Text.literal("No Effects").formatted(Formatting.GRAY));
        }
    }

    private String formatDuration(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private String toRomanNumeral(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> Integer.toString(number);
        };
    }


    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return "potions.elixirs." + this.id;
    }
}