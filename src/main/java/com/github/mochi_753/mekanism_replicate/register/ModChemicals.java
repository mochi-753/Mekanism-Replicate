package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import net.neoforged.bus.api.IEventBus;

public class ModChemicals {
    private static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekanismReplicate.MOD_ID);

    public static final DeferredChemical<Chemical> MIRACLE_SUBSTANCE = CHEMICALS.register("miracle_substance", 0xA020F0FF);

    public static void register(IEventBus bus) {
        CHEMICALS.register(bus);
    }
}
