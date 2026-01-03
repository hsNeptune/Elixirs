package org.hsneptune.elixirs.worldgen;

import java.util.List;

import org.hsneptune.elixirs.Elixirs;
import org.hsneptune.elixirs.worldgen.biome.ElixirsBiomes;
import org.hsneptune.elixirs.worldgen.biome.GlowingMushroomBiome;

import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.worldgen.biome.GlowingMushroomBiomeDim;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ElixirsTerraBlender implements TerraBlenderApi {

	private static final List<ElixirsBiomes> overworldBiomes = List.of(new GlowingMushroomBiome());

	public static final ElixirsTerraBlenderRegion OVERWORLD = new ElixirsTerraBlenderRegion(Identifier.of("elixirs", "overworld"), 
			RegionType.OVERWORLD, 2, overworldBiomes);

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(OVERWORLD);

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Elixirs.MOD_ID, ElixirsSurfaceRules.makeRule(overworldBiomes));
    }
}
