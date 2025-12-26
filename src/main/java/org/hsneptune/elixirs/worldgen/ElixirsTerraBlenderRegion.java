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
	private final List<ElixirsBiomes> biomes;

    public ElixirsTerraBlenderRegion(Identifier location, RegionType rType, int weight, List<ElixirsBiomes> biomes) {
        super(location, rType, weight);
		this.biomes = biomes;
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

		for (ElixirsBiomes biome : biomes) {
			biome.conditions().forEach(point -> builder.add(point, biome.key()));
		}

        builder.build().forEach(mapper);
    }
}
