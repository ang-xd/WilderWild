package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new QuiltItemSettings());
    public static final Block DEV_BLOCK = new Block(QuiltBlockSettings.copyOf(Blocks.STONE));

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
        registerBlock("test_1", DEV_BLOCK, ItemGroup.MISC);
    }

    private static void registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new QuiltItemSettings().group(group)));
    }
}
