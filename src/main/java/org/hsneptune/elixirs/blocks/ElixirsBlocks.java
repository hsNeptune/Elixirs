package org.hsneptune.elixirs.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsGroup;
import org.hsneptune.elixirs.blocks.custom.GlowingVines;


public class ElixirsBlocks {
    public static final Block POTENT_ORE = register(new Block(AbstractBlock.Settings.create()
            .strength(3.0f, 1.8f)
            .requiresTool()), "potent_ore", true); // This means a tool is required
    public static final Block GLOWING_MUSHROOM_STEM = register(new Block(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).luminance(ctx -> 5)), "glowing_mushroom_stem", true);
    public static final Block GLOWING_MUSHROOM_BLOCK = register(new Block(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK).luminance(ctx -> 10)), "glowing_mushroom_block", true);
    public static final Block GLOWING_MUSHROOM_FOLIAGE = register(new Block(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK).luminance(ctx -> 7)), "glowing_mushroom_foliage", true);
    public static final Block GLOWING_VINES = register(new GlowingVines(AbstractBlock.Settings.create().luminance(ctx -> 10)), "glowing_vines", true); 

    public static void initialize() {
        Elixirs.LOGGER.info("Initializing Mod Blocks");
    }

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(Elixirs.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        ElixirsGroup.addItem(block.asItem());
        return Registry.register(Registries.BLOCK, id, block);
    }
}
