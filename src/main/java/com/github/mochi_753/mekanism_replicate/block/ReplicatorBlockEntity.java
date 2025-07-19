package com.github.mochi_753.mekanism_replicate.block;

import com.github.mochi_753.mekanism_replicate.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReplicatorBlockEntity extends BlockEntity {
    public ReplicatorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.REPLICATOR.get(), pos, blockState);
    }

    public void tick() {

    }
}
