package org.hsneptune.elixirs.armor.turtle;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;

import java.util.Map;

import static org.hsneptune.elixirs.materials.ElixirsMaterial.createMaterial;

public class TurtleArmor {
    public static final RegistryEntry<ArmorMaterial> TURTLE = createMaterial("turtle",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 6,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.BOOTS, 2
            ),
            // Enchantability. For reference, leather has 15, iron has 9, and diamond has 10.
            17,
            // The sound played when the armor is equipped.
            SoundEvents.ITEM_ARMOR_EQUIP_TURTLE,
            // The ingredient(s) used to repair the armor.
            () -> Ingredient.ofItems(Items.TURTLE_SCUTE),
            0.0F,
            0.0F,
            // Turtle is NOT dyeable, so we will pass false.
            false);
    public static final int TURTLE_DURABILITY_MULTIPLIER = 25;
    public static final Item TURTLE_CHESTPLATE = ElixirsItems.register("turtle_chestplate", new ArmorItem(TURTLE, ArmorItem.Type.CHESTPLATE, (new Item.Settings()).maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(25))));
    public static final Item TURTLE_LEGGINGS = ElixirsItems.register("turtle_leggings", new ArmorItem(TURTLE, ArmorItem.Type.LEGGINGS, (new Item.Settings()).maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(25))));
    public static final Item TURTLE_BOOTS = ElixirsItems.register("turtle_boots", new ArmorItem(TURTLE, ArmorItem.Type.BOOTS, (new Item.Settings()).maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(25))));


    public static void initialize() {

        Elixirs.LOGGER.info("Turtle armor intialized");
    }

}
