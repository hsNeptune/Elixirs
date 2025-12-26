package org.hsneptune.elixirs.worldgen;


import java.util.List;

import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;

import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ElixirsSurfaceRules {

	public static MaterialRules.MaterialRule makeRule(List<ElixirsBiomes> biomes) {
		MaterialRules.MaterialRule[] rules = biomes.stream()
			.map(ElixirsBiomes::rule)
			.toArray(MaterialRules.MaterialRule[]::new);

		return MaterialRules.sequence(rules);
	}

}
