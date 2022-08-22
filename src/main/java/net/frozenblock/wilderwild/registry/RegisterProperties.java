package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.FlowerColors;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class RegisterProperties {

    //Osseous Sculk
    public static final IntegerProperty PILLAR_HEIGHT_LEFT = IntegerProperty.create("pillar_height_left", 0, 15);
    public static final BooleanProperty UPSIDE_DOWN = BooleanProperty.create("upside_down");
    public static final IntegerProperty TOTAL_HEIGHT = IntegerProperty.create("total_pillar_height", 0, 16);
    //Hanging Tendril
    public static final BooleanProperty TWITCHING = BooleanProperty.create("twitching");
    public static final BooleanProperty WRINGING_OUT = BooleanProperty.create("wringing_out");
    //Echo Glass
    public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 3);
    //Shelf Fungus
    public static final IntegerProperty FUNGUS_STAGE = IntegerProperty.create("shelf_fungus_stage", 1, 4);
    //Termite Mound
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
    //Glory of The Snow
    public static final EnumProperty<FlowerColors> FLOWER_COLOR = EnumProperty.create("flower_color", FlowerColors.class);
    //Cypress Roots
    public static final IntegerProperty ROOTS = IntegerProperty.create("roots", 1, 4);

    //Vanilla Blocks
    public static final IntegerProperty SOULS_TAKEN = IntegerProperty.create("souls_taken", 0, 2); //Sculk Shrieker
    public static final BooleanProperty HICCUPPING = BooleanProperty.create("hiccupping"); //Sculk Sensor

}
