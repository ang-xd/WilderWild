package net.frozenblock.wilderwild.world;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleEvents;

public class WilderWildSurfaceRules implements SurfaceRuleEvents.OverworldModifierCallback {

    @Override
    public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {

        MaterialRules.MaterialRule WATER = MaterialRules.block(Blocks.WATER.getDefaultState());

        context.materialRules().add(0,
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                                MaterialRules.condition(
                                        MaterialRules.biome(RegisterWorldgen.CYPRESS_WETLANDS),
                                        MaterialRules.condition(
                                                MaterialRules.aboveY(YOffset.fixed(60), 0),
                                                MaterialRules.condition(
                                                        MaterialRules.not(MaterialRules.aboveY(YOffset.fixed(63), 0)),
                                                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER)
                                                )
                                        )
                                )
                        )
                ));
    }
}
