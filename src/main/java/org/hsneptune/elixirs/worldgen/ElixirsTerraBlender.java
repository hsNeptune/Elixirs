package org.hsneptune.elixirs.worldgen;

import org.hsneptune.elixirs.Elixirs;

import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ElixirsTerraBlender implements TerraBlenderApi {

	public static final ElixirsTerraBlenderRegion glowing_mushroom = new ElixirsTerraBlenderRegion(Identifier.of("elixirs", "glowing_mushroom"), 2);

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(glowing_mushroom);

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Elixirs.MOD_ID, ElixirsSurfaceRules.makeRule());
    }
}
