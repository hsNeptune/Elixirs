package org.hsneptune.elixirs.worldgen.biome;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import terrablender.api.RegionType;

public interface ElixirsBiomes {

	public RegistryKey<Biome> key();
	public RegionType type();
	public MaterialRules.MaterialRule rule();
	public List<MultiNoiseUtil.NoiseHypercube> conditions();

    public static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
