package org.hsneptune.elixirs.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.hsneptune.elixirs.Elixirs;

import java.util.ArrayList;

public class ElixirsGroup {
    public static ArrayList<Item> items = new ArrayList<>();
    public static  CreativeModeTab ELIXIR_ITEM_GROUP;

    public static void registerItemGroup() {
        Elixirs.LOGGER.info("Registering item groups for Elixirs");
        ELIXIR_ITEM_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(Elixirs.MOD_ID, "elixir_item_group"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ElixirsItems.VIAL))
                        .displayItems((context, entries) -> { items.forEach(entries::accept); })
                        .title(Component.translatable("itemgroup.elixirs.elixir_items")).build());
    }

    public static void addItem(Item item){
        items.add(item);
    }
}
