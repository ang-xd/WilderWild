package net.frozenblock.wilderwild.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.WilderConfig;
import net.minecraft.client.ClientBrandRetriever;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

    @Inject(at = @At(value = "TAIL"), method = "getClientModName", cancellable = true, remap = false)
    private static void getClientModName(CallbackInfoReturnable<String> info) {
        WilderConfig.WildConfigJson config = WilderConfig.getConfig();
        if (config != null) {
            if (config.getOverwrite_Fabric()) {
                info.setReturnValue(QuiltLoader.isDevelopmentEnvironment() && !config.getIncludeWild() ? "vanilla" : "wilderwild");
            }
        }
    }

}