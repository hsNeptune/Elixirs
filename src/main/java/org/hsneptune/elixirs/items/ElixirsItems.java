package org.hsneptune.elixirs.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsItems {
    public static final Item SERUM = register("serum", new Item(new Item.Settings()));


    public static <T extends Item> T register(String path, T item) {
        // For versions below 1.21, please replace ''Identifier.of'' with ''new Identifier''
        ElixirsGroup.addItem(item);
        return Registry.register(Registries.ITEM, Identifier.of("elixirs", path), item);

    }

    public static void initialize() {
        LOGGER.info("Mod items initialized!");
    }
}
