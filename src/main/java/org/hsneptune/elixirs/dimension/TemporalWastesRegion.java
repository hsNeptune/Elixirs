package org.hsneptune.elixirs.dimension;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class TemporalWastesRegion extends Region {

    private static final RegistryKey<Biome> GLOWING_MUSHROOMS =
            RegistryKey.of(RegistryKeys.BIOME, Identifier.of("elixirs", "glowing_mushrooms"));

    public TemporalWastesRegion() {
        super(
                Identifier.of("elixirs", "temporal_wastes"),
                RegionType.OVERWORLD,
                5
        );
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {

        mapper.accept(Pair.of(
                new MultiNoiseUtil.NoiseHypercube(
                        MultiNoiseUtil.ParameterRange.of(-1.0f),
                        MultiNoiseUtil.ParameterRange.of(0.0f),
                        MultiNoiseUtil.ParameterRange.of(0.0f),
                        MultiNoiseUtil.ParameterRange.of(0.0f),
                        MultiNoiseUtil.ParameterRange.of(0.0f),
                        MultiNoiseUtil.ParameterRange.of(0.0f),
                        1
                ),
                GLOWING_MUSHROOMS
        ));
    }
}
