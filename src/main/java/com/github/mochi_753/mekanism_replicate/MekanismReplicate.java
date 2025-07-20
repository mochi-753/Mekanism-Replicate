package com.github.mochi_753.mekanism_replicate;

import com.github.mochi_753.mekanism_replicate.gui.ReplicatorScreen;
import com.github.mochi_753.mekanism_replicate.register.*;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

@Mod(MekanismReplicate.MOD_ID)
public class MekanismReplicate {
    public static final String MOD_ID = "mekanism_replicate";
    public static final String MOD_NAME = "Mekanism: Replicate";
    public static final String MOD_VERSION = "0.5.0";

    public static final Logger LOGGER = LogUtils.getLogger();

    public MekanismReplicate(IEventBus bus, ModContainer container) {
        ModBlocks.register(bus);
        ModBlockEntities.register(bus);
        ModItems.register(bus);
        ModTabs.register(bus);
        ModMenuTypes.register(bus);
        ModChemicals.register(bus);
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientSetup {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.REPLICATOR_MENU.get(), ReplicatorScreen::new);
        }
    }
}
