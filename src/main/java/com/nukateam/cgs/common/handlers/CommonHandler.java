package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.weapon.BaseWeaponRenderer;
import com.nukateam.cgs.client.renderers.weapon.FlintlockRenderer;
import com.nukateam.cgs.client.renderers.weapon.GatlingRenderer;
import com.nukateam.cgs.client.renderers.weapon.HammerRenderer;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.ntgl.CgsProjectileRegistry;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.registry.WeaponRegistry;
import net.minecraft.world.item.Item;
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
