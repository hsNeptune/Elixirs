package org.hsneptune.elixirs.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.hsneptune.elixirs.Elixirs;

public class Worldgen {
    public static final RegistryKey<PlacedFeature> POTENT_ORE_GENERATION = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("elixirs","potent_ore_generation"));


    public static void initialize() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, POTENT_ORE_GENERATION);
        Elixirs.LOGGER.info("Registering world generation");
    }
}
