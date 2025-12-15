package org.hsneptune.elixirs.tag;

import org.hsneptune.elixirs.Elixirs;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;


public class ElixirsTags {
    public static final TagKey<Block> GROWS_GLOWING_FOLIAGE =
        TagKey.of(RegistryKeys.BLOCK, Identifier.of("elixirs", "grows_glowing_foliage"));

    public static void initalize() {
        Elixirs.LOGGER.info("Initializing Tags");
    }
}
