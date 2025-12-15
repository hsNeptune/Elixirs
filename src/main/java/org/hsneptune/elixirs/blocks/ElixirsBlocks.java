package org.hsneptune.elixirs.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.items.ElixirsGroup;
import org.hsneptune.elixirs.blocks.custom.GlowingVines;
import net.minecraft.item.BoneMealItem;


public class ElixirsBlocks {
    public static final Block POTENT_ORE = register(new Block(BlockBehaviour.Properties.of()
            .strength(3.0f, 1.8f)
            .requiresCorrectToolForDrops()), "potent_ore", true); // This means a tool is required
    public static final Block GLOWING_MUSHROOM_STEM = register(new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM)), "glowing_mushroom_stem", true);
    public static final Block GLOWING_MUSHROOM_BLOCK = register(new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).lightLevel(ctx -> 7)), "glowing_mushroom_block", true);
    public static final Block GLOWING_MUSHROOM_FOLIAGE = register(new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).lightLevel(ctx -> 2)), "glowing_mushroom_foliage", true);
    public static final Block GLOWING_VINES = register(new GlowingVines(AbstractBlock.Settings.create().luminance(ctx -> 10)), "glowing_vines", true); 
    public static void initialize() {
        Elixirs.LOGGER.info("Initializing Mod Blocks");
    }

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.fromNamespaceAndPath(Elixirs.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }
        ElixirsGroup.addItem(block.asItem());
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }
}
