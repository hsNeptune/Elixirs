package org.hsneptune.elixirs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.World;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;
import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.effects.ElixirsEffects;
import org.hsneptune.elixirs.items.ElixirsGroup;
import org.hsneptune.elixirs.items.ElixirsItems;
import org.hsneptune.elixirs.worldgen.Worldgen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.block.WallBlock;

public class Elixirs implements ModInitializer {
	public static final String MOD_ID = "elixirs";
    
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ElixirsItems.initialize();
		ElixirsEffects.initialize();
		ElixirsBlocks.initialize();
		Worldgen.initialize();
		ElixirsGroup.registerItemGroup();

		LOGGER.info("Hello Fabric world!");
	}
    
}
