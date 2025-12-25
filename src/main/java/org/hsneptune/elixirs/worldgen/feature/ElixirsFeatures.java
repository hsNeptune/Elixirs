package org.hsneptune.elixirs.worldgen.feature;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;

public class ElixirsFeatures {

    public static final GlowingMushroomFeature BIG_MUSHROOM_FEATURE = Registry.register(Registries.FEATURE, Identifier.of("elixirs", "giant_glowing_mushroom"), new GlowingMushroomFeature());

    public static void initialize() {
        Elixirs.LOGGER.info("Registering features");
    }
}
