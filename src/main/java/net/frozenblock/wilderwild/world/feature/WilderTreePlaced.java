package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.minecraft.data.worldgen.placement.TreePlacements.SNOW_TREE_FILTER_DECORATOR;

public final class WilderTreePlaced {
    //BIRCH
    public static final Holder<PlacedFeature> NEW_BIRCH_CHECKED = PlacementUtils.register("new_birch_checked", WilderTreeConfigured.NEW_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_BIRCH_BEES_0004 = PlacementUtils.register("new_birch_bees_0004", WilderTreeConfigured.NEW_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> SHORT_BIRCH = PlacementUtils.register("short_birch", WilderTreeConfigured.SHORT_BIRCH, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SHORT_BIRCH_BEES_0004 = PlacementUtils.register("new_short_birch_bees_0004", WilderTreeConfigured.NEW_SHORT_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES_0004 = PlacementUtils.register("new_super_birch_bees_0004", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_SUPER_BIRCH_BEES = PlacementUtils.register("new_super_birch_bees", WilderTreeConfigured.NEW_SUPER_BIRCH_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_BIRCH_CHECKED = PlacementUtils.register("new_fallen_birch_checked", WilderTreeConfigured.NEW_FALLEN_BIRCH_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));

    //OAK
    public static final Holder<PlacedFeature> NEW_OAK_CHECKED = PlacementUtils.register("new_oak_checked", WilderTreeConfigured.NEW_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_OAK_BEES_0004 = PlacementUtils.register("new_oak_bees_00004", WilderTreeConfigured.NEW_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> SHORT_OAK_CHECKED = PlacementUtils.register("short_oak_checked", WilderTreeConfigured.SHORT_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_CHECKED = PlacementUtils.register("new_fancy_oak_checked", WilderTreeConfigured.NEW_FANCY_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES_0004 = PlacementUtils.register("new_fancy_oak_bees_0004", WilderTreeConfigured.NEW_FANCY_OAK_BEES_0004, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FANCY_OAK_BEES = PlacementUtils.register("new_fancy_oak_bees", WilderTreeConfigured.NEW_FANCY_OAK_BEES, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_OAK_CHECKED = PlacementUtils.register("new_fallen_oak_checked", WilderTreeConfigured.NEW_FALLEN_OAK_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    //DARK OAK
    public static final Holder<PlacedFeature> NEW_TALL_DARK_OAK_CHECKED = PlacementUtils.register("new_tall_dark_oak_checked", WilderTreeConfigured.NEW_TALL_DARK_OAK, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));

    //SWAMP TREE
    public static final Holder<PlacedFeature> NEW_SWAMP_TREE_CHECKED = PlacementUtils.register("new_swamp_tree_checked", WilderTreeConfigured.NEW_SWAMP_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE));

    //SPRUCE
    public static final Holder<PlacedFeature> NEW_SPRUCE_CHECKED = PlacementUtils.register("new_spruce_checked", WilderTreeConfigured.NEW_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> NEW_SPRUCE_ON_SNOW = PlacementUtils.register("new_spruce_on_snow", WilderTreeConfigured.NEW_SPRUCE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> NEW_SPRUCE_SHORT_CHECKED = PlacementUtils.register("new_spruce_short_checked", WilderTreeConfigured.NEW_SPRUCE_SHORT, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_CHECKED = PlacementUtils.register("fungus_pine_checked", WilderTreeConfigured.FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_PINE_ON_SNOW = PlacementUtils.register("fungus_pine_on_snow", WilderTreeConfigured.FUNGUS_PINE, SNOW_TREE_FILTER_DECORATOR);
    public static final Holder<PlacedFeature> MEGA_FUNGUS_SPRUCE_CHECKED = PlacementUtils.register("mega_fungus_spruce_checked", WilderTreeConfigured.MEGA_FUNGUS_SPRUCE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> MEGA_FUNGUS_PINE_CHECKED = PlacementUtils.register("mega_fungus_pine_checked", WilderTreeConfigured.MEGA_FUNGUS_PINE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));
    public static final Holder<PlacedFeature> FALLEN_SPRUCE_CHECKED = PlacementUtils.register("fallen_spruce_checked", WilderTreeConfigured.FALLEN_SPRUCE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING));

    //BAOBAB
    public static final Holder<PlacedFeature> BAOBAB = PlacementUtils.register("baobab", WilderTreeConfigured.BAOBAB, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_SAPLING));
    public static final Holder<PlacedFeature> BAOBAB_TALL = PlacementUtils.register("baobab_tall", WilderTreeConfigured.BAOBAB_TALL, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BAOBAB_SAPLING));

    //CYPRESS
    public static final Holder<PlacedFeature> CYPRESS = PlacementUtils.register("cypress", WilderTreeConfigured.CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> FUNGUS_CYPRESS = PlacementUtils.register("fungus_cypress", WilderTreeConfigured.FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_CYPRESS = PlacementUtils.register("short_cypress", WilderTreeConfigured.SHORT_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SHORT_FUNGUS_CYPRESS = PlacementUtils.register("short_fungus_cypress", WilderTreeConfigured.SHORT_FUNGUS_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> SWAMP_CYPRESS = PlacementUtils.register("swamp_cypress", WilderTreeConfigured.SWAMP_CYPRESS, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));
    public static final Holder<PlacedFeature> NEW_FALLEN_CYPRESS_CHECKED = PlacementUtils.register("new_fallen_cypress_checked", WilderTreeConfigured.NEW_FALLEN_CYPRESS_TREE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING));

    public static void registerTreePlaced() {
        WilderWild.logWild("Registering WilderTreePlaced for", true);
    }
}
