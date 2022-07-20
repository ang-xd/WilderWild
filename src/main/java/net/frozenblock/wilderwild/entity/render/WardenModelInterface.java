package net.frozenblock.wilderwild.entity.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

import java.util.List;

@InjectedInterface(WardenEntityModel.class)
public interface WardenModelInterface {

    List<ModelPart> getHeadAndTendrils();
}
