package com.nukateam.cgs;

import com.mojang.logging.LogUtils;
import com.nukateam.cgs.common.faundation.registry.*;
import com.nukateam.cgs.common.faundation.registry.items.*;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.ntgl.*;
import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(Gunsmithing.MOD_ID)
public class Gunsmithing {
    public static final String MOD_ID = "cgs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Gunsmithing(IEventBus MOD_EVENT_BUS, ModContainer container) {
        CgsWeapons.register(MOD_EVENT_BUS);
        CgsItems.register(MOD_EVENT_BUS);
        CgsAmmo.register(MOD_EVENT_BUS);
        CgsAttachments.register(MOD_EVENT_BUS);
        CgsBlocks.register(MOD_EVENT_BUS);
        CgsItemTabs.register(MOD_EVENT_BUS);
        CgsSounds.register(MOD_EVENT_BUS);
        CgsProjectiles.register(MOD_EVENT_BUS);
        CgsParticles.register(MOD_EVENT_BUS);
        CgsAmmoType.register();
        CgsAmmoHolders.register();
    }

    public static @NotNull ResourceLocation cgsResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(Gunsmithing.MOD_ID, name);
    }

    public static boolean isDebugging() {
        return !FMLEnvironment.production;
    }
}
