package org.hsneptune.elixirs.worldgen.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ElixirsBiomes {
    public static final RegistryKey<Biome> GLOWING_MUSHROOM_CAVES = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("elixirs", "glowing_mushroom")); 

    public static void bootstrap(Registerable<Biome> context) {
    }

}
