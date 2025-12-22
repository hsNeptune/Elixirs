package org.hsneptune.elixirs.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;

import java.util.ArrayList;

public class ElixirsGroup {
    public static ArrayList<Item> items = new ArrayList<>();
	public static ArrayList<ItemStack> itemStacks = new ArrayList<>();
    public static  ItemGroup ELIXIR_ITEM_GROUP;

    public static void registerItemGroup() {
        Elixirs.LOGGER.info("Registering item groups for Elixirs");
        ELIXIR_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(Elixirs.MOD_ID, "elixir_item_group"),
                FabricItemGroup.builder().icon(() -> new ItemStack(ElixirsItems.VIAL))
                        .entries((context, entries) -> { items.forEach(entries::add); itemStacks.forEach(entries::add); })
                        .displayName(Text.translatable("itemgroup.elixirs.elixir_items")).build());
    }

    public static void addItem(Item item){
        items.add(item);
    }
    public static void addItem(ItemStack item){
        itemStacks.add(item);
    }
}
