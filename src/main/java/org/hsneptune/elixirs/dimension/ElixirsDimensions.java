package org.hsneptune.elixirs.dimension;

import org.hsneptune.elixirs.Elixirs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import org.hsneptune.elixirs.armor.turtle.TurtleArmor;
import org.hsneptune.elixirs.worldgen.biome.GlowingMushroomBiomeDim;

import static org.hsneptune.elixirs.Elixirs.LOGGER;

public class ElixirsDimensions {
    private static final RegistryKey<DimensionOptions> TEMPORAL_WASTES_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes"));
    public static final RegistryKey<World> TEMPORAL_WASTES_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes"));
    public static final RegistryKey<DimensionType> TEMPORAL_WASTES_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(Elixirs.MOD_ID, "temporal_wastes_type"));

    public static void initialize() {
        GlowingMushroomBiomeDim.initialize();
        LOGGER.info("Dimensions initialized!");
    }
}
