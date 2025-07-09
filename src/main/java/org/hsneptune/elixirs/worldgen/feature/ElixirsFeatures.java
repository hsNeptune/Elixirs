package org.hsneptune.elixirs.worldgen.feature;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.worldgen.feature.GlowingMushroomFeature;

public class ElixirsFeatures {
    public static final Identifier BIG_MUSHROOM_ID = Identifier.of("elixirs", "giant_glowing_mushroom");

    public static final GlowingMushroomFeature BIG_MUSHROOM_FEATURE =
        Registry.register(Registries.FEATURE, BIG_MUSHROOM_ID, new GlowingMushroomFeature());
    public static void initialize() {
        Elixirs.LOGGER.info("Registering features");
    }
}
