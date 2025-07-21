package com.github.mochi_753.mekanism_replicate.block;

import mekanism.common.block.prefab.BlockTile;
import mekanism.common.content.blocktype.Machine;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;

public class ReplicatorBlock extends BlockTile.BlockTileModel<TileEntityReplicator, Machine<TileEntityReplicator>> {
    protected ReplicatorBlock(Machine<TileEntityReplicator> type) {
        super(type, BlockBehaviour.Properties.of().strength(4.0F, 6.0F).requiresCorrectToolForDrops().mapColor(MapColor.METAL));
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathType) {
        return false;
    }
}
