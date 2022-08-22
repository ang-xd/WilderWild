package net.frozenblock.wilderwild.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public final class WilderTreeDecorators {
    public static final TreeDecoratorType<ShelfFungusTreeDecorator> FUNGUS_TREE_DECORATOR = register("shelf_fungus_tree_decorator", ShelfFungusTreeDecorator.CODEC);
    public static final TreeDecoratorType<HeightBasedVineTrunkDecorator> HEIGHT_BASED_VINE_TRUNK_DECORATOR = register("height_based_vine_trunk_decorator", HeightBasedVineTrunkDecorator.CODEC);


    public static void generateTreeDecorators() {
        //Just to ensure the class is loaded.
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> register(String id, Codec<P> codec) {
        return Registry.register(Registry.TREE_DECORATOR_TYPES, WilderWild.id(id), new TreeDecoratorType<P>(codec));
    }
}
