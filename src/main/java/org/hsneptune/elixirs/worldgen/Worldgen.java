package org.hsneptune.elixirs.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.hsneptune.elixirs.Elixirs;

public class Worldgen {
    public static final ResourceKey<PlacedFeature> POTENT_ORE_GENERATION = ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath("elixirs","potent_ore_generation"));


    public static void initialize() {
        BiomeModifications.create(Identifier.fromNamespaceAndPath("elixirs", "glowing_mushroom")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), ctx -> {ctx.getSpawnSettings();}); 
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, POTENT_ORE_GENERATION);
        Elixirs.LOGGER.info("Registering world generation");
    }
}
