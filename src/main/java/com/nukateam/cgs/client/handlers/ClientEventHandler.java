package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.renderers.FireballRenderer;
import com.nukateam.cgs.common.faundation.registry.ModProjectiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.nukateam.cgs.client.renderers.*;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

     @SubscribeEvent
     public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
          event.registerEntityRenderer(ModProjectiles.FIREBALL.get(), FireballRenderer::new);
          event.registerEntityRenderer(ModProjectiles.ROCKET.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(ModProjectiles.NAIL.get(), CgsProjectileRenderer::new);
     }
}
