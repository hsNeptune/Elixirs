package org.hsneptune.elixirs.items;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;

import java.util.function.Function;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsItems {

    public static final Item VIAL = register("vial", Vial::new);
    public static final Item WATER_VIAL = register("water_vial", s -> new VialElixirs(s, false, "water_vial"), new Item.Settings().maxCount(1));
    public static final Item AWKWARD_VIAL = register("awkward_vial", s -> new VialElixirs(s, false, "awkward_vial"), new Item.Settings().maxCount(1));
    public static final Item DAMAGE_AFFINITY = register("damage_affinity", s -> new VialElixirs(s, false, "damage_affinity"), new Item.Settings().maxCount(1));
    public static final Item POTENT_DUST = register("potent_dust", Item::new);

    // Overload with default settings
    public static <T extends Item> T register(String path, Function<Item.Settings, T> factory) {
        return register(path, factory, new Item.Settings());
    }

    public static <T extends Item> T register(String path, Function<Item.Settings, T> function, Item.Settings settings) {

		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("elixirs", path));
        T item = function.apply(settings.registryKey(key));
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        ElixirsGroup.addItem(item);
        return Registry.register(Registries.ITEM, key, item);

    }





    public static void initialize() {
        TurtleArmor.initialize();
        LOGGER.info("Mod items initialized!");
    }
}
