package org.hsneptune.elixirs.items;

import com.mojang.serialization.Codec;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import org.apache.http.NoHttpResponseException;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.effects.ElixirsEffects;

import java.util.*;

public class VialElixirs extends PotionItem {
    private final List<RegistryEntry<StatusEffect>> effects = new ArrayList<>();
    private List<Integer> durations = new ArrayList<>();
    private List<Integer> amplifiers = new ArrayList<>();
    private final List<Style> colors = new ArrayList<>();
    private final Map<String, Style> lines = new LinkedHashMap<>();
    private final boolean isEffect;
    private final String id;

    public static final ComponentType<List<Integer>> DURATION = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of("elixirs", "duration"), ComponentType.<List<Integer>>builder().codec(Codec.list(Codec.INT)).build());
    public static final ComponentType<List<Integer>> AMPLIFIER = Registry.register(Registries.DATA_COMPONENT_TYPE,
            Identifier.of("elixirs", "amplifier"), ComponentType.<List<Integer>>builder().codec(Codec.list(Codec.INT)).build());

    public static final Item RAGE_SERUM_3M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
            .addEffect(ElixirsEffects.RAGE, 1200*3, 0, Formatting.RED)
            .addLine("x2 Damage Bonus", Formatting.BLUE)
            .addLine("x0.5 Damage Resistance", Formatting.RED));

    public static final Item RAGE_SERUM_8M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
                    .addEffect(ElixirsEffects.RAGE, 1200*8, 0, Formatting.RED)
                    .addLine("x2 Damage Bonus", Formatting.BLUE)
            .addLine("x0.5 Damage Resistance", Formatting.RED));

    public static final Item RAGE_SERUM_1M = ElixirsItems.register("rage_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "rage_serum")
                    .addEffect(ElixirsEffects.RAGE, (int) (1200*1.5), 1, Formatting.RED)
                    .addLine("x3 Damage Bonus", Formatting.BLUE)
            .addLine("x0.33 Damage Resistance", Formatting.RED));

    public static final Item STARRY_SERUM_3M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*3, 0, 0x8dc42f)
            .addLine("+7.7% Movement Speed", Formatting.BLUE)
            .addLine("2x Experience Bonus", Formatting.BLUE)
            .addLine("+2 Experience Gained per Second", Formatting.BLUE));

    public static final Item STARRY_SERUM_8M = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, 1200*8, 0, 0x8dc42f)
            .addLine("+7.7% Movement Speed", Formatting.BLUE)
            .addLine("2x Experience Bonus", Formatting.BLUE)
            .addLine("+2 Experience Gained per Second", Formatting.BLUE));

    public static final Item STARRY_SERUM_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*1.5), 1, 0x8dc42f)
            .addLine("+10.7% Movement Speed", Formatting.BLUE)
            .addLine("3x Experience Bonus", Formatting.BLUE)
            .addLine("+3 Experience Gained per Second", Formatting.BLUE));

    public static final Item STARRY_SERUM_SUPER_EFFECTIVE = ElixirsItems.register("starry_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "starry_serum")
            .addEffect(ElixirsEffects.STARRY, (int) (1200*.5), 2, 0x8dc42f)
            .addLine("+13.5% Movement Speed", Formatting.BLUE)
            .addLine("4x Experience Bonus", Formatting.BLUE)
            .addLine("+4 Experience Gained per Second", Formatting.BLUE));

    public static final Item MELEE_AFFINITY_SERUM_30S = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200*.5), 0, 0x8dc42f)
            .addLine("Immunity to Melee Damage", Formatting.BLUE)
            .addLine("Instant Death From a Projectile", Formatting.RED));
    public static final Item MELEE_AFFINITY_SERUM_1M = ElixirsItems.register("melee_affinity_serum", new VialElixirs(new Item.Settings().maxCount(1), true, "melee_affinity_serum")
            .addEffect(ElixirsEffects.MELEE_AFFINITY, (int) (1200), 0, 0x8dc42f)
            .addLine("Immunity to Melee Damage", Formatting.BLUE)
            .addLine("Instant Death From a Projectile", Formatting.RED));



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

    public VialElixirs addLine(String string, Formatting color ) {
        lines.put(string, Style.EMPTY.withColor(color));
        return this;
    }



    public VialElixirs addLine(String string, int color) {
        lines.put(string, Style.EMPTY.withColor(color));
        return this;
    }

    public void loadFromComponents(ItemStack stack) {
        List<Integer> durationComponents = stack.getOrDefault(DURATION, null);
        List<Integer> amplifierComponents = stack.getOrDefault(AMPLIFIER, null);

        LoreComponent lore = stack.getOrDefault(DataComponentTypes.LORE, null);
        if (durationComponents == null || amplifierComponents == null) {
            return;
        }
        this.lines.clear();
        if (lore != null) {

            for (Text loreLines : lore.lines()) {
                this.lines.put(loreLines.getString(), loreLines.getStyle());
            }

        }
        this.durations = durationComponents;
        this.amplifiers = amplifierComponents;


    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        loadFromComponents(stack);
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
        loadFromComponents(stack);
        if (!isEffect) {
            tooltip.add(Text.translatable("text.elixirs.default").formatted(Formatting.GRAY));
            return;
        }


        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i).value();
            int amplifier = amplifiers.get(i);
            int durationTicks = durations.get(i);
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
        for (Map.Entry<String, Style> entry : lines.entrySet()) {
            tooltip.add(Text.literal(entry.getKey()).setStyle(entry.getValue()));
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
