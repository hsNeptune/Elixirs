package org.hsneptune.elixirs.items;

import TypedActionResult;
import UseAction;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.hsneptune.elixirs.effects.ElixirsEffects;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;

public class VialElixirs extends PotionItem {
    private final List<Holder<MobEffect>> effects = new ArrayList<>();
    private List<Integer> durations = new ArrayList<>();
    private List<Integer> amplifiers = new ArrayList<>();
    private final List<Style> colors = new ArrayList<>();
    private final Map<String, Style> lines = new LinkedHashMap<>();
    private final List<Function<Integer, String>> functions = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();
    private final boolean isEffect;
    private final String id;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public static final DataComponentType<List<Integer>> DURATION = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("elixirs", "duration"), DataComponentType.<List<Integer>>builder().persistent(Codec.list(Codec.INT)).build());
    public static final DataComponentType<List<Integer>> AMPLIFIER = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("elixirs", "amplifier"), DataComponentType.<List<Integer>>builder().persistent(Codec.list(Codec.INT)).build());

    public static final Item RAGE_SERUM_3M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, 1200*3, 0, ChatFormatting.RED)
            .addLine("x%s Damage Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", ChatFormatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item RAGE_SERUM_8M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, 1200*8, 0, ChatFormatting.RED)
            .addLine("x%s Damage Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", ChatFormatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item RAGE_SERUM_1M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, (int) (1200*1.5), 1, ChatFormatting.RED)
            .addLine("x%s Damage Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("x%s Damage Resistance", ChatFormatting.RED, s -> (float) (1.0/(s + 2)), 0));

    public static final Item STARRY_SERUM_3M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*3, 0, 0x8dc42f)
            .addLine("+%s%% Movement Speed", ChatFormatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", ChatFormatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_8M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*8, 0, 0x8dc42f)
            .addLine("+%s%% Movement Speed", ChatFormatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", ChatFormatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*1.5), 1, 0x8dc42f)
            .addLine("+%s%% Movement Speed", ChatFormatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", ChatFormatting.BLUE, s -> (float) s + 2, 0));

    public static final Item STARRY_SERUM_SUPER_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*.5), 2, 0x8dc42f)
            .addLine("+%s%% Movement Speed", ChatFormatting.BLUE, s -> 100f * (float) (Math.atan(0.1*s + 0.25) * (2 / (2*Math.PI))), 0)
            .addLine("x%s Experience Bonus", ChatFormatting.BLUE, s -> (float) s + 2, 0)
            .addLine("+%s Experience Gained per Second", ChatFormatting.BLUE, s -> (float) s + 2, 0));

    public static final Item MELEE_AFFINITY_SERUM_30S = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200*.5), 0, 0x1a84d3)
            .addLine("Immunity to Melee Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Projectile", ChatFormatting.RED));
    public static final Item MELEE_AFFINITY_SERUM_1M = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200), 0, 0x1a84d3)
            .addLine("Immunity to Melee Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Projectile", ChatFormatting.RED));


    public static final Item PROJECTILE_AFFINITY_SERUM_30S = ElixirsItems.register("projectile_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "projectile_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200*.5), 0, 0xb55ce6)
            .addLine("Immunity to Projectile Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Melee Damage", ChatFormatting.RED));
    public static final Item PROJECTILE_AFFINITY_SERUM_1M = ElixirsItems.register("projectile_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "projectile_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200), 0, 0xb55ce6)
            .addLine("Immunity to Projectile Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Melee Damage", ChatFormatting.RED));
    public static final Item HEAD_AFFINITY_SERUM_30S = ElixirsItems.register("head_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "head_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200*.5), 0, 0xacb9c2)
            .addLine("Immunity to Projectile Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Melee Damage", ChatFormatting.RED));
    public static final Item HEAD_AFFINITY_SERUM_1M = ElixirsItems.register("head_affinity_serum", new VialElixirs(new Item.Properties().stacksTo(1), true, "head_affinity_serum")
            .addEffect(ElixirsEffects.PROJECTILE_AFFINITY, (int) (1200), 0, 0xacb9c2)
            .addLine("Immunity to Projectile Damage", ChatFormatting.BLUE)
            .addLine("Instakill by Melee Damage", ChatFormatting.RED));

    public VialElixirs(Properties settings, boolean isEffect, String id) {

        super(settings);

        this.isEffect = isEffect;
        this.id = id;

    }



    public VialElixirs addEffect(Holder<MobEffect> effect, int durationTicks, int amplifier, ChatFormatting color) {
        effects.add(effect);
        durations.add(durationTicks);
        amplifiers.add(amplifier);
        colors.add(Style.EMPTY.withColor(color));
        return this;
    }

    public VialElixirs addEffect(Holder<MobEffect> effect, int durationTicks, int amplifier, int color) {
        effects.add(effect);
        durations.add(durationTicks);
        amplifiers.add(amplifier);
        colors.add(Style.EMPTY.withColor(color));
        return this;
    }

    public VialElixirs addLine(String string, ChatFormatting color) {
        return addLine(string, color.getColor(), s -> (float) s, 0);
    }


    public VialElixirs addLine(String string, int color) {
        return addLine(string, color, s -> (float) s, 0);
    }



    public VialElixirs addLine(String string, ChatFormatting color, Function<Integer, Float> operator, int ampIndex) {

        return addLine(string, color.getColor(), operator, ampIndex);
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
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        List<Integer> stackDurations = stack.getOrDefault(DURATION, this.durations);
        List<Integer> stackAmplifiers = stack.getOrDefault(AMPLIFIER, this.amplifiers);
        if (user instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        for (int i = 0; i < effects.size(); i++) {
            Holder<MobEffect> effect = effects.get(i);
            int duration = stackDurations.get(i);
            int amplifier = stackAmplifiers.get(i);
            user.addEffect(new MobEffectInstance(effect, duration, amplifier));
        }


        return new ItemStack(ElixirsItems.VIAL);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        List<Integer> stackDurations = stack.getOrDefault(DURATION, this.durations);
        List<Integer> stackAmplifiers = stack.getOrDefault(AMPLIFIER, this.amplifiers);
        if (!isEffect) {
            tooltip.add(Component.translatable("text.elixirs.default").withStyle(ChatFormatting.GRAY));
            return;
        }


        for (int i = 0; i < effects.size(); i++) {
            MobEffect effect = effects.get(i).value();
            int amplifier = stackAmplifiers.get(i);
            int durationTicks = stackDurations.get(i);
            Style color = colors.get(i);

            String effectName = Component.translatable(effect.getDescriptionId()).getString();
            String ampText = toRomanNumeral(amplifier + 1);
            String durationText = formatDuration(durationTicks);

            tooltip.add(Component.literal(effectName + " " + ampText + " (" + durationText + ")").setStyle(color));
        }
        
        if (!this.lines.isEmpty()){
            tooltip.add(Component.literal(""));
            tooltip.add(Component.translatable("text.elixirs.when_applied").withStyle(ChatFormatting.DARK_PURPLE));
         }
        int count = 0;
        for (Map.Entry<String, Style> entry : lines.entrySet()) {

            tooltip.add(Component.literal(String.format(entry.getKey(), this.functions.get(count).apply(stackAmplifiers.get(indices.get(count))))).setStyle(entry.getValue()));
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
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return TypedActionResult.consume(user.getItemInHand(hand));
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
