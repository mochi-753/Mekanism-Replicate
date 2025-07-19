package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.block.ReplicatorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MekanismReplicate.MOD_ID);

    public static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }

    public static final Supplier<BlockEntityType<ReplicatorBlockEntity>> REPLICATOR =
            BLOCK_ENTITY_TYPES.register("replicator", () ->
                    BlockEntityType.Builder.of((ReplicatorBlockEntity::new), ModBlocks.REPLICATOR.get()).build(null)
            );


}
