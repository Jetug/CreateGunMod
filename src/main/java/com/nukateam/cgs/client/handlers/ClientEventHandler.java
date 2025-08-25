package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.renderers.FireballRenderer;
import com.nukateam.cgs.common.faundation.registry.CgsParticles;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.nukateam.cgs.client.renderers.*;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
     @SubscribeEvent
     public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
          event.registerEntityRenderer(CgsProjectiles.FIREBALL.get(), FireballRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.ROCKET.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.SMALL_ROCKET.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.NAIL.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.SPEAR.get(), CgsProjectileRenderer::new);
     }

     @SubscribeEvent
     public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
          var particleEngine = Minecraft.getInstance().particleEngine;
          particleEngine.register(CgsParticles.BLUE_FLAME.get(), FlameParticle.Provider::new);
     }
}
