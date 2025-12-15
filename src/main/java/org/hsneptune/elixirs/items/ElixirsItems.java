package org.hsneptune.elixirs.items;

import org.hsneptune.elixirs.armor.turtle.TurtleArmor;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ElixirsItems {

    public static final Item VIAL = register("vial", new Vial(new Item.Properties()));
    public static final Item WATER_VIAL = register("water_vial", new VialElixirs(new Item.Properties().stacksTo(1), false, "water_vial"));
    public static final Item AWKWARD_VIAL = register("awkward_vial", new VialElixirs(new Item.Properties().stacksTo(1), false, "awkward_vial"));
    public static final Item DAMAGE_AFFINITY = register("damage_affinity", new VialElixirs(new Item.Properties().stacksTo(1), false, "damage_affinity"));
    public static final Item POTENT_DUST = register("potent_dust", new Item(new Item.Properties()));


    public static <T extends Item> T register(String path, T item) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        ElixirsGroup.addItem(item);
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath("elixirs", path), item);

    }





    public static void initialize() {
        TurtleArmor.initialize();
        LOGGER.info("Mod items initialized!");
    }
}
