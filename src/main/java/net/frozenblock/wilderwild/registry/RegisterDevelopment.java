package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public final class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new QuiltItemSettings());

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
    }

    private static void registerBlock(String name, Block block, CreativeModeTab group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, CreativeModeTab group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new QuiltItemSettings().tab(group)));
    }
}
