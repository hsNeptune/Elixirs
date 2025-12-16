package org.hsneptune.elixirs.armor.turtle;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsItems;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;

import java.util.Map;

import static org.hsneptune.elixirs.materials.ElixirsMaterial.createMaterial;

public class TurtleArmor {
    public static final ArmorMaterial TURTLE = createMaterial("turtle", 31, Map.of(EquipmentType.HELMET, 2, EquipmentType.CHESTPLATE, 7, EquipmentType.LEGGINGS, 6, EquipmentType.BOOTS, 4), 
			13, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0, 2F);
    public static final Item TURTLE_CHESTPLATE = ElixirsItems.register("turtle_chestplate", s -> new Item(s.armor(TURTLE, EquipmentType.CHESTPLATE)));
    public static final Item TURTLE_LEGGINGS = ElixirsItems.register("turtle_leggings", s -> new Item(s.armor(TURTLE, EquipmentType.LEGGINGS)));
    public static final Item TURTLE_BOOTS = ElixirsItems.register("turtle_boots", s -> new Item(s.armor(TURTLE, EquipmentType.BOOTS)));

    public static void initialize() {

        Elixirs.LOGGER.info("Turtle armor intialized");
    }

}
