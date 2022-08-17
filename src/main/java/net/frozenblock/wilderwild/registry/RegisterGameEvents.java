package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public final class RegisterGameEvents {

    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);


    public static void registerEvents() {
        WilderWild.logWild("Registering GameEvents for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.GAME_EVENT, WilderWild.id("sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, WilderWild.id("hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);
    }
}
