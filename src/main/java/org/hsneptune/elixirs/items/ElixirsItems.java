package org.hsneptune.elixirs.items;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsItems {
    public static final Item VIAL = register("vial", new Item(new Item.Settings()));


    public static <T extends Item> T register(String path, T item) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        ElixirsGroup.addItem(item);
        return Registry.register(Registries.ITEM, Identifier.of("elixirs", path), item);

    }

    public static <T extends ArmorMaterial> T registerArmorMat(String path, T material) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        return Registry.register(Registries.ARMOR_MATERIAL, Identifier.of("elixirs", path), material);

    }

    public static void initialize() {
        TurtleArmor.initialize();
        LOGGER.info("Mod items initialized!");
    }
}
