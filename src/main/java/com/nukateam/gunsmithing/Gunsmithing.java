package com.nukateam.gunsmithing;

import com.mojang.logging.LogUtils;
import com.nukateam.gunsmithing.common.faundation.registry.*;
import com.nukateam.gunsmithing.common.network.PacketHandler;
import mod.azure.azurelib.animatable.GeoEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;


@Mod(Gunsmithing.MOD_ID)
public class Gunsmithing {
    public static final String MOD_ID = "gunsmithing";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Gunsmithing(FMLJavaModLoadingContext context) {
        var MOD_EVENT_BUS = context.getModEventBus();

        MOD_EVENT_BUS.addListener(this::commonSetup);
        ModGuns.register(MOD_EVENT_BUS);
        ModItems.register(MOD_EVENT_BUS);
        ModItemTabs.register(MOD_EVENT_BUS);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isDebugging() {
        return !FMLEnvironment.production;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }
}
