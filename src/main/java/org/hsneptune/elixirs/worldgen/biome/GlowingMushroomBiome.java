package org.hsneptune.elixirs.worldgen.biome;

import java.util.List;

import org.hsneptune.elixirs.blocks.ElixirsBlocks;
import org.hsneptune.elixirs.worldgen.ElixirsSurfaceRules;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.ParameterRange;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule;
import terrablender.api.ParameterUtils;
import terrablender.api.ParameterUtils.Continentalness;
import terrablender.api.ParameterUtils.Erosion;
import terrablender.api.ParameterUtils.Humidity;
import terrablender.api.ParameterUtils.Temperature;
import terrablender.api.RegionType;

public class GlowingMushroomBiome implements ElixirsBiomes {

    private static final MaterialRules.MaterialRule GLOWING_FOLIAGE = ElixirsBiomes.makeStateRule(ElixirsBlocks.GLOWING_MUSHROOM_FOLIAGE);
    private static final RegistryKey<Biome> GLOWING_MUSHROOM_CAVES = RegistryKey.of(RegistryKeys.BIOME, Identifier.of("elixirs", "glowing_mushroom")); 
	private static final List<MultiNoiseUtil.NoiseHypercube> CONDITIONS_LIST =  
        new ParameterUtils.ParameterPointListBuilder().temperature(Temperature.span(Temperature.COOL, Temperature.FROZEN))
            .humidity(Humidity.FULL_RANGE)
            .continentalness(Continentalness.FULL_RANGE)
            .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
            .depth(ParameterRange.of(0.75f, 1f))
            .weirdness(ParameterRange.of(0, 1)).build();

	@Override
	public List<NoiseHypercube> conditions() {
		// TODO Auto-generated method stub
		return CONDITIONS_LIST;
	}

	@Override
	public RegistryKey<Biome> key() {
		// TODO Auto-generated method stub
		return GLOWING_MUSHROOM_CAVES;
	}
	
	

	@Override
	public MaterialRule rule() {
		// TODO Auto-generated method stub
        MaterialRules.MaterialCondition inGlowingCaves =
            MaterialRules.biome(GLOWING_MUSHROOM_CAVES);

        // Replace ALL stone (including deepslate) with your foliage in your biome
        MaterialRules.MaterialRule replaceAllStone = MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.surface(), GLOWING_FOLIAGE)
                );

        return MaterialRules.condition(inGlowingCaves, replaceAllStone);
	}

	@Override
	public RegionType type() {
		// TODO Auto-generated method stub
		return RegionType.OVERWORLD;
	}


}
