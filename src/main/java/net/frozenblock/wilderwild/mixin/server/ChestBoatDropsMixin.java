package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.WilderBoats;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public class ChestBoatDropsMixin {

    //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

    @Inject(method = "getDropItem", at = @At("HEAD"), cancellable = true)
    public void getDropItem(CallbackInfoReturnable<Item> ci) {
        if (((ChestBoat) (Object) this).getBoatType() == WilderBoats.BAOBAB) {
            ci.setReturnValue(RegisterItems.BAOBAB_CHEST_BOAT_ITEM);
            ci.cancel();
        }
        if (((ChestBoat) (Object) this).getBoatType() == WilderBoats.CYPRESS) {
            ci.setReturnValue(RegisterItems.CYPRESS_CHEST_BOAT_ITEM);
            ci.cancel();
        }
    }

}
