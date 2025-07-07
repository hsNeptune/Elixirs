package org.hsneptune.elixirs.items;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.hsneptune.elixirs.effects.ElixirsEffects;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

public class VialElixirs extends PotionItem {
    private final List<RegistryEntry<StatusEffect>> effects = new ArrayList<>();
    private List<Integer> durations = new ArrayList<>();
    private List<Integer> amplifiers = new ArrayList<>();
    private final List<Style> colors = new ArrayList<>();
    private final Map<String, Style> lines = new LinkedHashMap<>();
    private final List<Function<Integer, String>> functions = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();
    private final boolean isEffect;
    private final String id;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public static final ComponentType<List<Integer>> DURATION = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of("elixirs", "duration"), ComponentType.<List<Integer>>builder().codec(Codec.list(Codec.INT)).build());
    public static final ComponentType<List<Integer>> AMPLIFIER = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of("elixirs", "amplifier"), ComponentType.<List<Integer>>builder().codec(Codec.list(Codec.INT)).build());

    public static final Item RAGE_SERUM_3M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, 1200*3, 0, Formatting.RED)
            .addLine("x%s Damage Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", Formatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item RAGE_SERUM_8M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, 1200*8, 0, Formatting.RED)
            .addLine("x%s Damage Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", Formatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item RAGE_SERUM_1M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, (int) (1200*1.5), 1, Formatting.RED)
            .addLine("x%s Damage Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", Formatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item STARRY_SERUM_3M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*3, 0, 0x8dc42f)
            .addLine("+%s%% Movement Speed", Formatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", Formatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_8M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*8, 0, 0x8dc42f)
            .addLine("+%s%% Movement Speed", Formatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", Formatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*1.5), 1, 0x8dc42f)
            .addLine("+%s%% Movement Speed", Formatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", Formatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_SUPER_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*.5), 2, 0x8dc42f)
            .addLine("+%s%% Movement Speed", Formatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", Formatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", Formatting.BLUE, s -> (float) s + 2, 0));

    public static final Item MELEE_AFFINITY_SERUM_30S = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200*.5), 0, 0x1a84d3)
            .addLine("Immunity to Melee Damage", Formatting.BLUE)
            .addLine("Instakill by Projectile", Formatting.RED));
    public static final Item MELEE_AFFINITY_SERUM_1M = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200), 0, 0x1a84d3)
            .addLine("Immunity to Melee Damage", Formatting.BLUE)
            .addLine("Instakill by Projectile", Formatting.RED));


    public static final Item PROJECTILE_AFFINITY_SERUM_30S = ElixirsItems.register("projectile_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "projectile_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200*.5), 0, 0xb55ce6)
            .addLine("Immunity to Projectile Damage", Formatting.BLUE)
            .addLine("Instakill by Melee Damage", Formatting.RED));
    public static final Item PROJECTILE_AFFINITY_SERUM_1M = ElixirsItems.register("projectile_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "projectile_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200), 0, 0xb55ce6)
            .addLine("Immunity to Projectile Damage", Formatting.BLUE)
            .addLine("Instakill by Melee Damage", Formatting.RED));
    public static final Item HEAD_AFFINITY_SERUM_30S = ElixirsItems.register("head_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "head_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200*.5), 0, 0xacb9c2)
            .addLine("Immunity to Projectile Damage", Formatting.BLUE)
            .addLine("Instakill by Melee Damage", Formatting.RED));
    public static final Item HEAD_AFFINITY_SERUM_1M = ElixirsItems.register("head_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "head_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200), 0, 0xacb9c2)
            .addLine("Immunity to Projectile Damage", Formatting.BLUE)
            .addLine("Instakill by Melee Damage", Formatting.RED));

    public VialElixirs(Settings settings, boolean isEffect, String id) {

        super(settings);

        this.isEffect = isEffect;
        this.id = id;

    }



    public VialElixirs addEffect(RegistryEntry<StatusEffect> effect, int durationTicks, int amplifier, Formatting color) {
        effects.add(effect);
        durations.add(durationTicks);
        amplifiers.add(amplifier);
        colors.add(Style.EMPTY.withColor(color));
        return this;
    }

    public VialElixirs addEffect(RegistryEntry<StatusEffect> effect, int durationTicks, int amplifier, int color) {
        effects.add(effect);
        durations.add(durationTicks);
        amplifiers.add(amplifier);
        colors.add(Style.EMPTY.withColor(color));
        return this;
    }

    public VialElixirs addLine(String string, Formatting color) {
        return addLine(string, color.getColorValue(), s -> (float) s, 0);
    }


    public VialElixirs addLine(String string, int color) {
        return addLine(string, color, s -> (float) s, 0);
    }



    public VialElixirs addLine(String string, Formatting color, Function<Integer, Float> operator, int ampIndex) {

        return addLine(string, color.getColorValue(), operator, ampIndex);
    }



    private VialElixirs addLine(String string, int color, Function<Integer, Float> operator, int ampIndex) {
        lines.put(string, Style.EMPTY.withColor(color));
        Function<Integer, String> newOperator = s -> decimalFormat.format(operator.apply(s));
        this.functions.add(newOperator);
        this.indices.add(ampIndex);
        return this;
    }

    public void loadFromComponents(ItemStack stack) {
        List<Integer> durationComponents = stack.getOrDefault(DURATION, null);
        List<Integer> amplifierComponents = stack.getOrDefault(AMPLIFIER, null);

        if (durationComponents == null || amplifierComponents == null) {
            return;
        }
        this.durations = durationComponents;
        this.amplifiers = amplifierComponents;


    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        List<Integer> stackDurations = stack.getOrDefault(DURATION, this.durations);
        List<Integer> stackAmplifiers = stack.getOrDefault(AMPLIFIER, this.amplifiers);
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

        for (int i = 0; i < effects.size(); i++) {
            RegistryEntry<StatusEffect> effect = effects.get(i);
            int duration = stackDurations.get(i);
            int amplifier = stackAmplifiers.get(i);
            user.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        }


        return new ItemStack(ElixirsItems.VIAL);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<Integer> stackDurations = stack.getOrDefault(DURATION, this.durations);
        List<Integer> stackAmplifiers = stack.getOrDefault(AMPLIFIER, this.amplifiers);
        if (!isEffect) {
            tooltip.add(Text.translatable("text.elixirs.default").formatted(Formatting.GRAY));
            return;
        }


        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i).value();
            int amplifier = stackAmplifiers.get(i);
            int durationTicks = stackDurations.get(i);
            Style color = colors.get(i);

            String effectName = Text.translatable(effect.getTranslationKey()).getString();
            String ampText = toRomanNumeral(amplifier + 1);
            String durationText = formatDuration(durationTicks);

            tooltip.add(Text.literal(effectName + " " + ampText + " (" + durationText + ")").setStyle(color));
        }
        
        if (!this.lines.isEmpty()){
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("text.elixirs.when_applied").formatted(Formatting.DARK_PURPLE));
         }
        int count = 0;
        for (Map.Entry<String, Style> entry : lines.entrySet()) {

            tooltip.add(Text.literal(String.format(entry.getKey(), this.functions.get(count).apply(stackAmplifiers.get(indices.get(count))))).setStyle(entry.getValue()));
            count++;
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
