package net.frozenblock.wilderwild.world;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.worldgen.surface_rule.api.SurfaceRuleEvents;

public class WilderWildSurfaceRules implements SurfaceRuleEvents.OverworldModifierCallback {

    @Override
    public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {

        var WATER = SurfaceRules.state(Blocks.WATER.defaultBlockState());

        context.materialRules().add(0,
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(RegisterWorldgen.CYPRESS_WETLANDS),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0)),
                                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0), WATER)
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
