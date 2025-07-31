package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.block.TileEntityReplicator;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;

public class ModBlocks {
    private static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismReplicate.MOD_ID);

    public static final BlockRegistryObject<BlockTile.BlockTileModel<TileEntityReplicator, Machine<TileEntityReplicator>>, ItemBlockTooltip<BlockTile.BlockTileModel<TileEntityReplicator, Machine<TileEntityReplicator>>>> REPLICATOR
            = BLOCKS.registerDetails("replicator", () -> new BlockTile.BlockTileModel<>(ModBlockTypes.REPLICATOR, properties -> properties.mapColor(MapColor.METAL)))
            .forItemHolder(holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                            .addBasic(TileEntityReplicator.MAX_CHEMICAL)
                            .build())
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .build())
            );

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
