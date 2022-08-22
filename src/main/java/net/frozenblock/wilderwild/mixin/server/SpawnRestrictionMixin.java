package net.frozenblock.wilderwild.mixin.server;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

@Mixin(SpawnPlacements.class)
public class SpawnRestrictionMixin {

    @Final
    @Shadow
    private static Map<EntityType<?>, SpawnPlacements.Data> DATA_BY_TYPE;

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static <T extends Mob> void register(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate, CallbackInfo info) {
        if (type == EntityType.SLIME) {
            info.cancel();
            SpawnPlacements.Data entry = DATA_BY_TYPE.put(type, new SpawnPlacements.Data(heightmapType, SpawnPlacements.Type.NO_RESTRICTIONS, predicate));
            if (entry != null) {
                throw new IllegalStateException("Duplicate registration for type " + Registry.ENTITY_TYPE.getKey(type));
            }
        }
    }

}
