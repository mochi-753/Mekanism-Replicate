package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister
            .create(BuiltInRegistries.CREATIVE_MODE_TAB, MekanismReplicate.MOD_ID);

    public static final Supplier<CreativeModeTab> MEKANISM_REPLICATE_TAB = TABS.register("mekanism_replicate_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.FURNACE))
                    .title(Component.translatable("creative_tab.mekanism_replicate.mekanism_replicate_tab"))
                    .displayItems((itemDisplayParam, output) -> {
                        output.accept(ModItems.REPLICATOR.get());
                    }).build()
    );

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
