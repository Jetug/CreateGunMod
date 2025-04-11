package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.hud.SteamGunHud;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
     @SubscribeEvent
     public static void registerHud(RegisterGuiOverlaysEvent event){
         event.registerAboveAll("ammo_steam", SteamGunHud.HUD);
     }
}
