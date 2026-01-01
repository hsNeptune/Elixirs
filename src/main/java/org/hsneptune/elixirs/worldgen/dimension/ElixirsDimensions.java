package org.hsneptune.elixirs.worldgen.dimension;

import net.minecraft.world.biome.Biome;
import org.hsneptune.elixirs.Elixirs;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;

import java.awt.*;
import java.util.OptionalLong;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsDimensions {
    private static final RegistryKey<DimensionOptions> TEMPORAL_WASTES_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes"));
    public static final RegistryKey<World> TEMPORAL_WASTES_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes"));
    public static final RegistryKey<DimensionType> TEMPORAL_WASTES_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes_type"));

    public static void initialize() {
        TurtleArmor.initialize();
        LOGGER.info("Dimensions initialized!");
    }
}
