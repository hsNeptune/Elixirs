package org.hsneptune.elixirs.worldgen;

import java.util.function.Consumer;

import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.worldgen.RegionUtils;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import terrablender.api.ParameterUtils.*;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class ElixirsTerraBlenderRegion extends Region{
    public ElixirsTerraBlenderRegion(Identifier location, int weight) {
        super(location, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder().temperature(Temperature.span(Temperature.COOL, Temperature.FROZEN))
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.FULL_RANGE)
            .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
            .depth(ParameterRange.of(0.75f, 1f))
            .weirdness(ParameterRange.of(0, 1))
            .build().forEach(point -> builder.add(point, ElixirsBiomes.GLOWING_MUSHROOM_CAVES));

        builder.build().forEach(mapper);
    }
}
