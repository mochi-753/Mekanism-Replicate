package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(BuiltInRegistries.ITEM, MekanismReplicate.MOD_ID);

    public static final Supplier<Item> REPLICATOR = ITEMS.register("replicator",
            () -> new BlockItem(ModBlocks.REPLICATOR.get(), new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
