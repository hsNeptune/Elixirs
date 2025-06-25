package org.hsneptune.elixirs.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;
import org.hsneptune.elixirs.effects.ElixirsEffects;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsItems {

    public static final Item VIAL = register("vial", new Vial(new Item.Settings()));
    public static final Item WATER_VIAL = register("water_vial", new VialElixirs(new Item.Settings().maxCount(1), false, "water_vial"));
    public static final Item AWKWARD_VIAL = register("awkward_vial", new VialElixirs(new Item.Settings().maxCount(1), false, "awkward_vial"));
    public static final Item POTENT_DUST = register("potent_dust", new Item(new Item.Settings()));


    public static <T extends Item> T register(String path, T item) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        ElixirsGroup.addItem(item);
        return Registry.register(Registries.ITEM, Identifier.of("elixirs", path), item);

    }





    public static void initialize() {
        TurtleArmor.initialize();
        LOGGER.info("Mod items initialized!");
    }
}
