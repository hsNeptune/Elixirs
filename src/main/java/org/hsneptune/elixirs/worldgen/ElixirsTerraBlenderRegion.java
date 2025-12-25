package org.hsneptune.elixirs.worldgen;

import java.util.List;
import java.util.function.Consumer;

import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import terrablender.api.ParameterUtils.*;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange;

public class ElixirsTerraBlenderRegion extends Region {

	private static final List<MultiNoiseUtil.NoiseHypercube> conditions_GLOWING_MUSHROOM =  
        new ParameterUtils.ParameterPointListBuilder().temperature(Temperature.span(Temperature.COOL, Temperature.FROZEN))
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.FULL_RANGE)
            .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
            .depth(ParameterRange.of(0.75f, 1f))
            .weirdness(ParameterRange.of(0, 1)).build();

    public ElixirsTerraBlenderRegion(Identifier location, int weight) {
        super(location, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        conditions_GLOWING_MUSHROOM.forEach(point -> builder.add(point, ElixirsBiomes.GLOWING_MUSHROOM_CAVES));

        builder.build().forEach(mapper);
    }
}
