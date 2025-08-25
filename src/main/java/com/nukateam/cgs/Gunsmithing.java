package com.nukateam.cgs;

import com.mojang.logging.LogUtils;
import com.nukateam.cgs.common.faundation.registry.*;
import com.nukateam.cgs.common.faundation.registry.items.*;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.ntgl.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;


@Mod(Gunsmithing.MOD_ID)
public class Gunsmithing {
    public static final String MOD_ID = "cgs";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public Gunsmithing() {
        MOD_EVENT_BUS.addListener(this::commonSetup);
        CgsWeapons.register(MOD_EVENT_BUS);
        ModItems.register(MOD_EVENT_BUS);
        AttachmentItems.register(MOD_EVENT_BUS);
        ModBlocks.register(MOD_EVENT_BUS);
        ModItemTabs.register(MOD_EVENT_BUS);
        ModSounds.register(MOD_EVENT_BUS);
        ModProjectiles.register(MOD_EVENT_BUS);
        MinecraftForge.EVENT_BUS.register(this);
        CgsAmmoType.register();
        CgsAmmo.register();
    }

    public static @NotNull ResourceLocation cgsResource(String name) {
        return new ResourceLocation(Gunsmithing.MOD_ID, name);
    }

    public static boolean isDebugging() {
        return !FMLEnvironment.production;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }
}
