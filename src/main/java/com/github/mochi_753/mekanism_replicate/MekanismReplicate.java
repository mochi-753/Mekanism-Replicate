package com.github.mochi_753.mekanism_replicate;

import com.github.mochi_753.mekanism_replicate.register.ModBlocks;
import com.github.mochi_753.mekanism_replicate.register.ModChemicals;
import com.github.mochi_753.mekanism_replicate.register.ModTileEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(MekanismReplicate.MOD_ID)
public class MekanismReplicate {
    public static final String MOD_ID = "mekanism_replicate";
    public static final String MOD_VERSION = "0.6.1";

    public MekanismReplicate(IEventBus bus, Dist dist) {
        ModBlocks.register(bus);
        ModChemicals.register(bus);
        ModTileEntityTypes.register(bus);
    }
}
