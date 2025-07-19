package com.github.mochi_753.mekanism_replicate;

import com.github.mochi_753.mekanism_replicate.register.ModBlockEntities;
import com.github.mochi_753.mekanism_replicate.register.ModBlocks;
import com.github.mochi_753.mekanism_replicate.register.ModItems;
import com.github.mochi_753.mekanism_replicate.register.ModTabs;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(MekanismReplicate.MOD_ID)
public class MekanismReplicate {
    public static final String MOD_ID = "mekanism_replicate";
    public static final String MOD_NAME = "Mekanism: Replicate";
    public static final String MOD_VERSION = "0.2.1";

    public static final Logger LOGGER = LogUtils.getLogger();

    public MekanismReplicate(IEventBus bus, ModContainer container) {
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        ModItems.register(bus);
        ModTabs.register(bus);
    }
}
