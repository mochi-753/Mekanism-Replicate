package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.block.ReplicatorBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister
            .create(BuiltInRegistries.BLOCK, MekanismReplicate.MOD_ID);

    public static final Supplier<Block> REPLICATOR = BLOCKS.register("replicator",
            () -> new ReplicatorBlock(BlockBehaviour.Properties.of()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
