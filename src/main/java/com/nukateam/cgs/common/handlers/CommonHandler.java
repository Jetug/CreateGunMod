package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.ntgl.CgsProjectileRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonHandler {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CgsProjectileRegistry::registerProjectiles);
    }
}
