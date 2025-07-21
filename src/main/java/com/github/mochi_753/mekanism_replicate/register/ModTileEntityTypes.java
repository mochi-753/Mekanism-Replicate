package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.block.TileEntityReplicator;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.neoforged.bus.api.IEventBus;

public class ModTileEntityTypes {
    private static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MekanismReplicate.MOD_ID);
    public static final TileEntityTypeRegistryObject<TileEntityReplicator> REPLICATOR = TILE_ENTITY_TYPES
            .mekBuilder(ModBlocks.REPLICATOR, TileEntityReplicator::new)
            .serverTicker(TileEntityMekanism::tickServer)
            .build();

    public static void register(IEventBus bus) {
        TILE_ENTITY_TYPES.register(bus);
    }

    private void ModBlockTypes() {
    }
}
