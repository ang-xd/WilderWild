package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.*;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.item.AlgaeItem;
import net.frozenblock.wilderwild.item.FloweredLilyPadItem;
import net.frozenblock.wilderwild.misc.FlowerColors;
import net.frozenblock.wilderwild.mixin.server.SignTypeAccessor;
import net.frozenblock.wilderwild.world.gen.sapling.BaobabSaplingGenerator;
import net.frozenblock.wilderwild.world.gen.sapling.CypressSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.block.content.registry.api.FlammableBlockEntry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.block.extensions.api.QuiltMaterialBuilder;
import org.quiltmc.qsl.item.content.registry.api.ItemContentRegistries;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;

import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterItems.BAOBAB_SIGN;
import static net.frozenblock.wilderwild.registry.RegisterItems.CYPRESS_SIGN;

public final class RegisterBlocks {
    private static final MaterialColor BAOBAB_PLANKS_COLOR = MaterialColor.COLOR_ORANGE;
    private static final MaterialColor BAOBAB_BARK_COLOR = MaterialColor.COLOR_BROWN;
    private static final MaterialColor CYPRESS_PLANKS_COLOR = MaterialColor.COLOR_LIGHT_GRAY;
    private static final MaterialColor CYPRESS_BARK_COLOR = MaterialColor.STONE;

    // OTHER (BUILDING BLOCKS)
    public static final net.minecraft.world.level.block.Block CHISELED_MUD_BRICKS = new net.minecraft.world.level.block.Block(QuiltBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS).strength(1.5F).requiresCorrectToolForDrops().sound(SoundType.MUD_BRICKS));

    public static void registerOtherBB() {
        // BB = Building Blocks
        registerBlock("chiseled_mud_bricks", CHISELED_MUD_BRICKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
    }

    // WOOD
    public static final net.minecraft.world.level.block.Block BAOBAB_PLANKS = new net.minecraft.world.level.block.Block(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_PLANKS = new net.minecraft.world.level.block.Block(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_LOG = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_LOG = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block STRIPPED_BAOBAB_LOG = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block STRIPPED_CYPRESS_LOG = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block STRIPPED_BAOBAB_WOOD = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block STRIPPED_CYPRESS_WOOD = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_WOOD = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? BAOBAB_PLANKS_COLOR : BAOBAB_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_WOOD = new RotatedPillarBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? CYPRESS_PLANKS_COLOR : CYPRESS_BARK_COLOR).strength(2.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_SLAB = new SlabBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_SLAB = new SlabBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_STAIRS = new StairBlock(BAOBAB_PLANKS.defaultBlockState(), QuiltBlockSettings.copy(BAOBAB_PLANKS));
    public static final net.minecraft.world.level.block.Block CYPRESS_STAIRS = new StairBlock(CYPRESS_PLANKS.defaultBlockState(), QuiltBlockSettings.copy(CYPRESS_PLANKS));

    public static final net.minecraft.world.level.block.Block BAOBAB_BUTTON = new WoodButtonBlock(QuiltBlockSettings.copy(Blocks.OAK_BUTTON).color(BAOBAB_PLANKS_COLOR));
    public static final net.minecraft.world.level.block.Block CYPRESS_BUTTON = new WoodButtonBlock(QuiltBlockSettings.copy(Blocks.OAK_BUTTON).color(CYPRESS_PLANKS_COLOR));

    public static final net.minecraft.world.level.block.Block BAOBAB_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).noCollission().strength(0.5F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).noCollission().strength(0.5F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_DOOR = new DoorBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
    public static final net.minecraft.world.level.block.Block CYPRESS_DOOR = new DoorBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion());

    public static final net.minecraft.world.level.block.Block BAOBAB_TRAPDOOR = new TrapDoorBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(RegisterBlocks::never));
    public static final net.minecraft.world.level.block.Block CYPRESS_TRAPDOOR = new TrapDoorBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(RegisterBlocks::never));

    public static final net.minecraft.world.level.block.Block BAOBAB_FENCE_GATE = new FenceGateBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_FENCE_GATE = new FenceGateBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS.defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final net.minecraft.world.level.block.Block BAOBAB_SAPLING = new SaplingBlock(new BaobabSaplingGenerator(), QuiltBlockSettings.of(net.minecraft.world.level.material.Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    public static final net.minecraft.world.level.block.Block POTTED_BAOBAB_SAPLING = new FlowerPotBlock(RegisterBlocks.BAOBAB_SAPLING, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.DECORATION).instabreak().noOcclusion());
    public static final net.minecraft.world.level.block.Block CYPRESS_SAPLING = new WaterloggableSaplingBlock(new CypressSaplingGenerator(), QuiltBlockSettings.copyOf(Blocks.BIRCH_SAPLING));
    public static final net.minecraft.world.level.block.Block POTTED_CYPRESS_SAPLING = new FlowerPotBlock(RegisterBlocks.CYPRESS_SAPLING, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.DECORATION).instabreak().noOcclusion());

    public static final net.minecraft.world.level.block.Block BAOBAB_LEAVES = new LeavesBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(RegisterBlocks::canSpawnOnLeaves).isSuffocating(RegisterBlocks::never).isViewBlocking(RegisterBlocks::never));
    public static final net.minecraft.world.level.block.Block CYPRESS_LEAVES = new LeavesBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(RegisterBlocks::canSpawnOnLeaves).isSuffocating(RegisterBlocks::never).isViewBlocking(RegisterBlocks::never));

    public static final net.minecraft.world.level.block.Block BAOBAB_FENCE = new FenceBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final net.minecraft.world.level.block.Block CYPRESS_FENCE = new FenceBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_PLANKS_COLOR).strength(2.0F, 3.0F).sound(SoundType.WOOD));

    public static final WoodType BAOBAB_SIGN_TYPE = SignTypeAccessor.newSignType("wilderwildbaobab");
    public static final net.minecraft.world.level.block.Block BAOBAB_SIGN_BLOCK = new WilderSignBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), BAOBAB_SIGN_TYPE);
    public static final net.minecraft.world.level.block.Block BAOBAB_WALL_SIGN = new WilderWallSignBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, BAOBAB_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(BAOBAB_SIGN_BLOCK), BAOBAB_SIGN_TYPE);

    public static final WoodType CYPRESS_SIGN_TYPE = SignTypeAccessor.newSignType("wilderwildcypress");
    public static final net.minecraft.world.level.block.Block CYPRESS_SIGN_BLOCK = new WilderSignBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD), CYPRESS_SIGN_TYPE);
    public static final net.minecraft.world.level.block.Block CYPRESS_WALL_SIGN = new WilderWallSignBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, CYPRESS_LOG.defaultMaterialColor()).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(CYPRESS_SIGN_BLOCK), CYPRESS_SIGN_TYPE);

    public static void registerWoods() {
        String baobab = "baobab";
        String cypress = "cypress";

        SignTypeAccessor.registerNew(BAOBAB_SIGN_TYPE);
        SignTypeAccessor.registerNew(CYPRESS_SIGN_TYPE);

        registerBlock(baobab + "_planks", BAOBAB_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_planks", CYPRESS_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_log", BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_log", CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_log", STRIPPED_BAOBAB_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_log", STRIPPED_CYPRESS_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock("stripped_" + baobab + "_wood", STRIPPED_BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock("stripped_" + cypress + "_wood", STRIPPED_CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_wood", BAOBAB_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_wood", CYPRESS_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_slab", BAOBAB_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_slab", CYPRESS_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_stairs", BAOBAB_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
        registerBlock(cypress + "_stairs", CYPRESS_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);

        registerBlock(baobab + "_button", BAOBAB_BUTTON, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_button", CYPRESS_BUTTON, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_pressure_plate", BAOBAB_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_pressure_plate", CYPRESS_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_door", BAOBAB_DOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_door", CYPRESS_DOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_trapdoor", BAOBAB_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_trapdoor", CYPRESS_TRAPDOOR, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_fence_gate", BAOBAB_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
        registerBlock(cypress + "_fence_gate", CYPRESS_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);

        registerBlock(baobab + "_sapling", BAOBAB_SAPLING, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + baobab + "_sapling", POTTED_BAOBAB_SAPLING);
        registerBlock(cypress + "_sapling", CYPRESS_SAPLING, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_" + cypress + "_sapling", POTTED_CYPRESS_SAPLING);

        registerBlock(baobab + "_leaves", BAOBAB_LEAVES, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_leaves", CYPRESS_LEAVES, CreativeModeTab.TAB_DECORATIONS);

        registerBlock(baobab + "_fence", BAOBAB_FENCE, CreativeModeTab.TAB_DECORATIONS);
        registerBlock(cypress + "_fence", CYPRESS_FENCE, CreativeModeTab.TAB_DECORATIONS);

        Registry.register(Registry.ITEM, WilderWild.id("baobab_sign"), BAOBAB_SIGN);
        registerBlockWithoutBlockItem(baobab + "_sign", BAOBAB_SIGN_BLOCK);
        registerBlockWithoutBlockItem(baobab + "_wall_sign", BAOBAB_WALL_SIGN);
        Registry.register(Registry.ITEM, WilderWild.id("cypress_sign"), CYPRESS_SIGN);
        registerBlockWithoutBlockItem(cypress + "_sign", CYPRESS_SIGN_BLOCK);
        registerBlockWithoutBlockItem(cypress + "_wall_sign", CYPRESS_WALL_SIGN);
    }

    // HOLLOWED LOGS
    public static final net.minecraft.world.level.block.Block HOLLOWED_OAK_LOG = createHollowedLogBlock(MaterialColor.WOOD, MaterialColor.PODZOL);
    public static final net.minecraft.world.level.block.Block HOLLOWED_SPRUCE_LOG = createHollowedLogBlock(MaterialColor.PODZOL, MaterialColor.COLOR_BROWN);
    public static final net.minecraft.world.level.block.Block HOLLOWED_BIRCH_LOG = createHollowedLogBlock(MaterialColor.SAND, MaterialColor.QUARTZ);
    public static final net.minecraft.world.level.block.Block HOLLOWED_JUNGLE_LOG = createHollowedLogBlock(MaterialColor.DIRT, MaterialColor.PODZOL);
    public static final net.minecraft.world.level.block.Block HOLLOWED_ACACIA_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.STONE);
    public static final net.minecraft.world.level.block.Block HOLLOWED_DARK_OAK_LOG = createHollowedLogBlock(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
    public static final net.minecraft.world.level.block.Block HOLLOWED_MANGROVE_LOG = createHollowedLogBlock(MaterialColor.COLOR_RED, MaterialColor.PODZOL);
    public static final net.minecraft.world.level.block.Block HOLLOWED_BAOBAB_LOG = createHollowedLogBlock(MaterialColor.COLOR_ORANGE, MaterialColor.COLOR_BROWN);
    public static final net.minecraft.world.level.block.Block HOLLOWED_CYPRESS_LOG = createHollowedLogBlock(MaterialColor.COLOR_LIGHT_GRAY, MaterialColor.STONE);

    public static void registerHollowedLogs() {
        registerBlock("hollowed_oak_log", HOLLOWED_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_spruce_log", HOLLOWED_SPRUCE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_birch_log", HOLLOWED_BIRCH_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_jungle_log", HOLLOWED_JUNGLE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_acacia_log", HOLLOWED_ACACIA_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_dark_oak_log", HOLLOWED_DARK_OAK_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_mangrove_log", HOLLOWED_MANGROVE_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_baobab_log", HOLLOWED_BAOBAB_LOG, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hollowed_cypress_log", HOLLOWED_CYPRESS_LOG, CreativeModeTab.TAB_DECORATIONS);
    }

    // SCULK
    public static final net.minecraft.world.level.block.Block SCULK_STAIRS = new SculkStairsBlock(Blocks.SCULK.defaultBlockState(), QuiltBlockSettings.of(net.minecraft.world.level.material.Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final net.minecraft.world.level.block.Block SCULK_SLAB = new SculkSlabBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final net.minecraft.world.level.block.Block SCULK_WALL = new SculkWallBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.SCULK).strength(0.2F).sound(SoundType.SCULK).dropsLike(Blocks.SCULK));
    public static final net.minecraft.world.level.block.Block OSSEOUS_SCULK = new OsseousSculkBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(2.0F).sound(RegisterBlockSoundGroups.OSSEOUS_SCULK));
    public static final net.minecraft.world.level.block.Block HANGING_TENDRIL = new HangingTendrilBlock(QuiltBlockSettings.copyOf(Blocks.SCULK_SENSOR).strength(0.7F).collidable(false).lightLevel((state) -> 1)
            .sound(RegisterBlockSoundGroups.HANGING_TENDRIL).emissiveRendering((state, world, pos) -> HangingTendrilBlock.shouldHavePogLighting(state)), 4);
    public static final net.minecraft.world.level.block.Block ECHO_GLASS = new EchoGlassBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.GLASS, MaterialColor.COLOR_CYAN).strength(0.3F).noOcclusion().sound(RegisterBlockSoundGroups.ECHO_GLASS));

    public static void registerDeepDark() {
        registerBlock("sculk_stairs", SCULK_STAIRS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_slab", SCULK_SLAB, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("sculk_wall", SCULK_WALL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("osseous_sculk", OSSEOUS_SCULK, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("hanging_tendril", HANGING_TENDRIL, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("echo_glass", ECHO_GLASS, CreativeModeTab.TAB_DECORATIONS);
    }

    // MISC
    private static final Material ALGAE_MATERIAL = new QuiltMaterialBuilder(MaterialColor.PLANT)
            .allowsMovement()
            .lightPassesThrough()
            .notSolid()
            .destroyedByPiston()
            .build();

    public static final net.minecraft.world.level.block.Block TERMITE_MOUND = new TermiteMound(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD, MaterialColor.COLOR_BROWN).strength(0.3F).sound(RegisterBlockSoundGroups.COARSEDIRT));

    // PLANTS
    public static final net.minecraft.world.level.block.Block SEEDING_DANDELION = new SeedingDandelionBlock(MobEffects.SLOW_FALLING, 12, QuiltBlockSettings.copy(Blocks.DANDELION).sound(SoundType.SPORE_BLOSSOM).strength(0.0F).noOcclusion());
    public static final net.minecraft.world.level.block.Block POTTED_SEEDING_DANDELION = new FlowerPotBlock(RegisterBlocks.SEEDING_DANDELION, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.DECORATION).instabreak().noOcclusion());
    public static final net.minecraft.world.level.block.Block CARNATION = new FlowerBlock(MobEffects.REGENERATION, 12, QuiltBlockSettings.copy(Blocks.DANDELION).sound(SoundType.SPORE_BLOSSOM).strength(0.0F).noOcclusion());
    public static final net.minecraft.world.level.block.Block POTTED_CARNATION = new FlowerPotBlock(RegisterBlocks.CARNATION, QuiltBlockSettings.of(net.minecraft.world.level.material.Material.DECORATION).instabreak().noOcclusion());
    public static final net.minecraft.world.level.block.Block GLORY_OF_THE_SNOW = new GloryOfTheSnowBlock(QuiltBlockSettings.copy(Blocks.DANDELION).sound(SoundType.SPORE_BLOSSOM).strength(0.0F).noOcclusion().randomTicks(), List.of(FlowerColors.BLUE, FlowerColors.PINK, FlowerColors.PURPLE, FlowerColors.WHITE));

    public static final net.minecraft.world.level.block.Block WHITE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(QuiltBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.QUARTZ).sound(SoundType.VINE));
    public static final net.minecraft.world.level.block.Block PINK_GLORY_OF_THE_SNOW = new FlowerLichenBlock(QuiltBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.CRIMSON_STEM).sound(SoundType.VINE));
    public static final net.minecraft.world.level.block.Block PURPLE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(QuiltBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_PURPLE).sound(SoundType.VINE));
    public static final net.minecraft.world.level.block.Block BLUE_GLORY_OF_THE_SNOW = new FlowerLichenBlock(QuiltBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.COLOR_BLUE).sound(SoundType.VINE));

    public static final net.minecraft.world.level.block.Block DATURA = new TallFlowerBlock(QuiltBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).noOcclusion());
    public static final net.minecraft.world.level.block.Block MILKWEED = new MilkweedBlock(QuiltBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0F).noOcclusion());

    public static final net.minecraft.world.level.block.Block CATTAIL = new WaterloggableTallFlowerBlock(QuiltBlockSettings.copy(Blocks.ROSE_BUSH).sound(SoundType.WET_GRASS).strength(0.0F).noOcclusion());
    public static final net.minecraft.world.level.block.Block FLOWERING_LILY_PAD = new FloweringLilyPadBlock(QuiltBlockSettings.copy(Blocks.LILY_PAD).sound(RegisterBlockSoundGroups.LILYPAD));

    public static final net.minecraft.world.level.block.Block ALGAE = new AlgaeBlock(QuiltBlockSettings.of(ALGAE_MATERIAL).instabreak().speedFactor(0.6F).noOcclusion().noCollission().sound(SoundType.SLIME_BLOCK));

    public static void registerPlants() {
        registerBlock("seeding_dandelion", SEEDING_DANDELION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_seeding_dandelion", POTTED_SEEDING_DANDELION);
        registerBlock("carnation", CARNATION, CreativeModeTab.TAB_DECORATIONS);
        registerBlockWithoutBlockItem("potted_carnation", POTTED_CARNATION);
        registerBlock("glory_of_the_snow", GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("blue_giant_glory_of_the_snow", BLUE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("pink_giant_glory_of_the_snow", PINK_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("violet_beauty_glory_of_the_snow", PURPLE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("alba_glory_of_the_snow", WHITE_GLORY_OF_THE_SNOW, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("datura", DATURA, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("milkweed", MILKWEED, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("cattail", CATTAIL, CreativeModeTab.TAB_DECORATIONS);

    }

    public static final net.minecraft.world.level.block.Block BROWN_SHELF_FUNGUS = new ShelfFungusBlock(QuiltBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK).luminance(1).collidable(false).noOcclusion().sound(RegisterBlockSoundGroups.MUSHROOM));
    public static final net.minecraft.world.level.block.Block RED_SHELF_FUNGUS = new ShelfFungusBlock(QuiltBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).collidable(false).noOcclusion().sound(RegisterBlockSoundGroups.MUSHROOM));
    public static final net.minecraft.world.level.block.Block POLLEN_BLOCK = new FlowerLichenBlock(QuiltBlockSettings.copyOf(Blocks.GRASS).collidable(false).offsetType(BlockBehaviour.OffsetType.NONE).color(MaterialColor.SAND).sound(SoundType.VINE));

    public static void registerNotSoPlants() {
        registerBlockWithoutBlockItem("pollen", POLLEN_BLOCK);
        registerBlock("brown_shelf_fungus", BROWN_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
        registerBlock("red_shelf_fungus", RED_SHELF_FUNGUS, CreativeModeTab.TAB_DECORATIONS);
    }

    // BLOCK FAMILIES
    public static final BlockFamily BAOBAB = BlockFamilies.familyBuilder(BAOBAB_PLANKS)
            .button(BAOBAB_BUTTON)
            .slab(BAOBAB_SLAB)
            .stairs(BAOBAB_STAIRS)
            .fence(BAOBAB_FENCE)
            .fenceGate(BAOBAB_FENCE_GATE)
            .pressurePlate(BAOBAB_PRESSURE_PLATE)
            .sign(BAOBAB_SIGN_BLOCK, BAOBAB_WALL_SIGN)
            .door(BAOBAB_DOOR)
            .trapdoor(BAOBAB_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();


    public static final BlockFamily CYPRESS = BlockFamilies.familyBuilder(CYPRESS_PLANKS)
            .button(CYPRESS_BUTTON)
            .slab(CYPRESS_SLAB)
            .stairs(CYPRESS_STAIRS)
            .fence(CYPRESS_FENCE)
            .fenceGate(CYPRESS_FENCE_GATE)
            .pressurePlate(CYPRESS_PRESSURE_PLATE)
            .sign(CYPRESS_SIGN_BLOCK, CYPRESS_WALL_SIGN)
            .door(CYPRESS_DOOR)
            .trapdoor(CYPRESS_TRAPDOOR)
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .getFamily();

    public static final net.minecraft.world.level.block.Block NULL_BLOCK = new net.minecraft.world.level.block.Block(QuiltBlockSettings.copyOf(Blocks.STONE).sound(RegisterBlockSoundGroups.NULL_BLOCK)); // B) -merp

    // HELLO EVERYBODY
    // hi - treetrain
    public static void registerBlocks() {
        WilderWild.logWild("Registering Blocks for", WilderWild.UNSTABLE_LOGGING);

        registerOtherBB();
        registerWoods();
        registerHollowedLogs();
        registerDeepDark();
        registerBlock("termite_mound", TERMITE_MOUND, CreativeModeTab.TAB_DECORATIONS);
        registerPlants();
        Registry.register(Registry.BLOCK, WilderWild.id("flowering_lily_pad"), FLOWERING_LILY_PAD);
        Registry.register(Registry.ITEM, WilderWild.id("flowering_lily_pad"), new FloweredLilyPadItem(FLOWERING_LILY_PAD, new QuiltItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
        Registry.register(Registry.BLOCK, WilderWild.id("algae"), ALGAE);
        Registry.register(Registry.ITEM, WilderWild.id("algae"), new AlgaeItem(ALGAE, new QuiltItemSettings().tab(CreativeModeTab.TAB_DECORATIONS)));
        registerBlock("null_block", NULL_BLOCK, CreativeModeTab.TAB_MISC);
        registerNotSoPlants();

        registerComposting();
        registerFlammability();
        registerFuels();
    }

    private static void registerBlockWithoutBlockItem(String name, net.minecraft.world.level.block.Block block) {
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlock(String name, net.minecraft.world.level.block.Block block, CreativeModeTab group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, net.minecraft.world.level.block.Block block, CreativeModeTab group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new QuiltItemSettings().tab(group)));
    }

    private static HollowedLogBlock createHollowedLogBlock(MaterialColor topMapColor, MaterialColor sideMapColor) {
        return new HollowedLogBlock(QuiltBlockSettings.of(net.minecraft.world.level.material.Material.WOOD,
                        (state) -> state.getValue(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                .strength(2.0F).sound(RegisterBlockSoundGroups.HOLLOWED_LOG));
    }

    public static void addBaobab() {
        BlockContentRegistries.STRIPPABLE_BLOCK.put(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        BlockContentRegistries.STRIPPABLE_BLOCK.put(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_LOG, HOLLOWED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_BAOBAB_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_LOG, STRIPPED_BAOBAB_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(BAOBAB_WOOD, STRIPPED_BAOBAB_WOOD);

        BlockContentRegistries.STRIPPABLE_BLOCK.put(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        BlockContentRegistries.STRIPPABLE_BLOCK.put(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);

        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_LOG, HOLLOWED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_LOG, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
        TermiteMoundBlockEntity.Termite.addDegradable(STRIPPED_CYPRESS_WOOD, Blocks.AIR);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_LOG, STRIPPED_CYPRESS_LOG);
        TermiteMoundBlockEntity.Termite.addNaturalDegradable(CYPRESS_WOOD, STRIPPED_CYPRESS_WOOD);
    }

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockView, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static void registerComposting() {
        ItemContentRegistries.COMPOST_CHANCE.put(CARNATION.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(CATTAIL.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(DATURA.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(MILKWEED.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(RegisterItems.MILKWEED_POD, 0.25F);
        ItemContentRegistries.COMPOST_CHANCE.put(SEEDING_DANDELION.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(FLOWERING_LILY_PAD.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(BROWN_SHELF_FUNGUS.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(RED_SHELF_FUNGUS.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(CYPRESS_LEAVES.asItem(), 0.3F);
        ItemContentRegistries.COMPOST_CHANCE.put(BAOBAB_LEAVES.asItem(), 0.3F);
        ItemContentRegistries.COMPOST_CHANCE.put(BAOBAB_SAPLING.asItem(), 0.3F);
        ItemContentRegistries.COMPOST_CHANCE.put(CYPRESS_SAPLING.asItem(), 0.3F);
        ItemContentRegistries.COMPOST_CHANCE.put(GLORY_OF_THE_SNOW.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(BLUE_GLORY_OF_THE_SNOW.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(WHITE_GLORY_OF_THE_SNOW.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(PINK_GLORY_OF_THE_SNOW.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(PURPLE_GLORY_OF_THE_SNOW.asItem(), 0.65F);
        ItemContentRegistries.COMPOST_CHANCE.put(ALGAE.asItem(), 0.3F);
    }

    private static void registerFlammability() {
        WilderWild.logWild("Registering Flammability for", WilderWild.UNSTABLE_LOGGING);
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.POLLEN_BLOCK, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.SEEDING_DANDELION, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CARNATION, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CATTAIL, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.DATURA, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.MILKWEED, new FlammableBlockEntry(100, 60));

        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_BIRCH_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_OAK_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_ACACIA_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_JUNGLE_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_MANGROVE_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_SPRUCE_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_BAOBAB_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.STRIPPED_BAOBAB_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_WOOD, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.STRIPPED_BAOBAB_WOOD, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_PLANKS, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_STAIRS, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_DOOR, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_FENCE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_SLAB, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_FENCE_GATE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_PRESSURE_PLATE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_TRAPDOOR, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_LEAVES, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_BUTTON, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_SIGN_BLOCK, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.BAOBAB_WALL_SIGN, new FlammableBlockEntry(5, 20));

        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.HOLLOWED_CYPRESS_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.STRIPPED_CYPRESS_LOG, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_WOOD, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.STRIPPED_CYPRESS_WOOD, new FlammableBlockEntry(5, 5));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_PLANKS, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_STAIRS, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_DOOR, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_FENCE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_SLAB, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_FENCE_GATE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_PRESSURE_PLATE, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_TRAPDOOR, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_LEAVES, new FlammableBlockEntry(100, 60));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_BUTTON, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_SIGN_BLOCK, new FlammableBlockEntry(5, 20));
        BlockContentRegistries.FLAMMABLE_BLOCK.put(RegisterBlocks.CYPRESS_WALL_SIGN, new FlammableBlockEntry(5, 20));
    }

    private static void registerFuels() {
        WilderWild.logWild("Registering Fuels for", WilderWild.UNSTABLE_LOGGING);
        RegistryEntryAttachment<Item, Integer> registry = ItemContentRegistries.FUEL_TIME;

        registry.put(BAOBAB_FENCE.asItem(), 300);
        registry.put(BAOBAB_FENCE_GATE.asItem(), 300);
        registry.put(CYPRESS_FENCE.asItem(), 300);
        registry.put(CYPRESS_FENCE_GATE.asItem(), 300);
    }
}

