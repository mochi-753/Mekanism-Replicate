package com.github.mochi_753.mekanism_replicate.register;

import com.github.mochi_753.mekanism_replicate.MekanismReplicate;
import com.github.mochi_753.mekanism_replicate.gui.ReplicatorMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister
            .create(BuiltInRegistries.MENU, MekanismReplicate.MOD_ID);

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }    public static final DeferredHolder<MenuType<?>, MenuType<ReplicatorMenu>> REPLICATOR_MENU = MENUS.register(
            "replicator", () -> IMenuTypeExtension.create(ReplicatorMenu::new)
    );


}
