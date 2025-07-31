package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicateLang;
import com.github.mochi_753.mekanism_replicate.block.TileEntityReplicator;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeSideConfig;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.registries.MekanismSounds;

public class ModBlockTypes {
    public static final Machine<TileEntityReplicator> REPLICATOR = Machine.MachineBuilder
            .createMachine(() -> ModTileEntityTypes.REPLICATOR, MekanismReplicateLang.DESCRIPTION_REPLICATOR)
            .withEnergyConfig(MekanismConfig.usage.antiprotonicNucleosynthesizer, MekanismConfig.usage.antiprotonicNucleosynthesizer)
            .withSound(MekanismSounds.ANTIPROTONIC_NUCLEOSYNTHESIZER)
            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY)
            .withComputerSupport("replicator")
            .with(AttributeUpgradeSupport.SPEED_ENERGY)
            .with(AttributeSideConfig.ELECTRIC_MACHINE)
            .build();

    private ModBlockTypes() {
    }
}
