package net.frozenblock.wilderwild;

import com.chocohead.mm.api.ClassTinkerers;
import org.quiltmc.loader.api.MappingResolver;
import org.quiltmc.loader.api.QuiltLoader;


public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver remapper = QuiltLoader.getMappingResolver();

        String spawnGroup = remapper.mapClassName("intermediary", "net.minecraft.class_1311");
        ClassTinkerers.enumBuilder(spawnGroup, String.class, int.class, boolean.class, boolean.class, int.class).addEnum("WILDERWILDFIREFLIES", "wilderwildfireflies", 56, true, false, 80).build();
    }
}
