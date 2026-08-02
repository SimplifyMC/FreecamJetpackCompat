package com.heyyczer.fjcompat;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FJCompat.MODID)
public class FJCompat {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "freecamjetpackcompat";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public FJCompat(IEventBus modEventBus, ModContainer modContainer) {
        //
    }

}
