package org.hsneptune.elixirs.worldgen.biome;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.Elixirs;
import terrablender.api.ParameterUtils;
import terrablender.api.RegionType;

import java.util.List;

public class GlowingMushroomBiomeDim implements ElixirsBiomes{
    private static final MaterialRules.MaterialRule GLOWING_FOLIAGE_DIM = ElixirsBiomes.makeStateRule(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE);
    private static final RegistryKey<Biome> GLOWING_MUSHROOM_CAVES_DIM = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("elixirs", "glowing_mushroom_dim"));
    private static final List<MultiNoiseUtil.NoiseHypercube> CONDITIONS_LIST =
            new ParameterUtils.ParameterPointListBuilder().temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.FROZEN))
                    .humidity(ParameterUtils.Humidity.FULL_RANGE)
                    .continentalness(ParameterUtils.Continentalness.FULL_RANGE)
                    .erosion(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_1)
                    .depth(MultiNoiseUtil.ParameterRange.of(0.75f, 1f))
                    .weirdness(MultiNoiseUtil.ParameterRange.of(0, 1)).build();

    @Override
    public List<MultiNoiseUtil.NoiseHypercube> conditions() {
        // TODO Auto-generated method stub
        return CONDITIONS_LIST;
    }

    @Override
    public RegistryKey<Biome> key() {
        // TODO Auto-generated method stub
        return GLOWING_MUSHROOM_CAVES_DIM;
    }



    @Override
    public MaterialRules.MaterialRule rule() {
        // TODO Auto-generated method stub
        MaterialRules.MaterialCondition inGlowingCaves =
                MaterialRules.biome(GLOWING_MUSHROOM_CAVES_DIM);

        // Replace ALL stone (including deepslate) with your foliage in your biome
        MaterialRules.MaterialRule replaceAllStone = MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.surface(), GLOWING_FOLIAGE_DIM)
        );

        return MaterialRules.condition(inGlowingCaves, replaceAllStone);
    }

    @Override
    public RegionType type() {
        return null;
    }

    public static void initialize(){
        Elixirs.LOGGER.info("Glowing Mushrooms Dim Edition Initialized!");
    }


}
