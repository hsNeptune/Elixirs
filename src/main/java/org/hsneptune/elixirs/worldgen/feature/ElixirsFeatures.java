package org.hsneptune.elixirs.worldgen.feature;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;

public class ElixirsFeatures {
    public static final Identifier BIG_MUSHROOM_ID = Identifier.of("elixirs", "giant_glowing_mushroom");

    public static final GlowingMushroomFeature BIG_MUSHROOM_FEATURE =
        Registry.register(Registries.FEATURE, BIG_MUSHROOM_ID, new GlowingMushroomFeature());
    public static void initialize() {
        Elixirs.LOGGER.info("Registering features");
    }
}
