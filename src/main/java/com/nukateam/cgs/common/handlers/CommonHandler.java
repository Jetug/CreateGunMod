package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.ntgl.CgsProjectileRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = Gunsmithing.MOD_ID)
public class CommonHandler {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CgsProjectileRegistry::registerProjectiles);
    }
}
