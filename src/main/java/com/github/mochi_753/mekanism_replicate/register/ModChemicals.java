package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import mekanism.common.registration.impl.SlurryRegistryObject;
import mekanism.common.resource.PrimaryResource;
import net.neoforged.bus.api.IEventBus;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModChemicals {
    public static final Map<PrimaryResource, SlurryRegistryObject<Chemical, Chemical>> PROCESSED_RESOURCES = new LinkedHashMap<>();
    private static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekanismReplicate.MOD_ID);
    public static final DeferredChemical<Chemical> MIRACLE_SUBSTANCE = CHEMICALS.register(
            "miracle_substance", 0x800080FF
    );

    public static void register(IEventBus bus) {
        CHEMICALS.register(bus);
    }
}
