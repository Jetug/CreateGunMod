package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.renderers.FireballRenderer;
import com.nukateam.cgs.common.faundation.registry.ModProjectiles;
import com.nukateam.ntgl.client.render.renderers.misc.AshPileRenderer;
import com.nukateam.ntgl.client.render.renderers.misc.FlyingGibsRenderer;
import com.nukateam.ntgl.client.render.renderers.projectiles.*;
import com.nukateam.ntgl.common.foundation.init.ModEntityTypes;
import com.nukateam.ntgl.common.foundation.init.Projectiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

     @SubscribeEvent
     public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
          event.registerEntityRenderer(ModProjectiles.FIREBALL.get(), FireballRenderer::new);
     }
}
