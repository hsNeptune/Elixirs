package org.hsneptune.elixirs.worldgen;

import org.hsneptune.elixirs.Elixirs;

import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ElixirsTerraBlender implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ElixirsTerraBlenderRegion(Identifier.of("elixirs", "glowing_mushroom"), 2));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Elixirs.MOD_ID, ElixirsSurfaceRules.makeRules());
    }
}
