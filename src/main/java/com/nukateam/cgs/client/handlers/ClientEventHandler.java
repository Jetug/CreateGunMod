package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.renderers.projectile.CgsProjectileRenderer;
import com.nukateam.cgs.client.renderers.projectile.FireballRenderer;
import com.nukateam.cgs.common.faundation.registry.CgsParticles;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import com.nukateam.ntgl.client.render.renderers.projectiles.ProjectileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Gunsmithing.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
     @SubscribeEvent
     public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
          event.registerEntityRenderer(CgsProjectiles.FIREBALL.get(), FireballRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.ROCKET.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.SMALL_ROCKET.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.NAIL.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.SPEAR.get(), CgsProjectileRenderer::new);
          event.registerEntityRenderer(CgsProjectiles.INCENDIARY.get(), ProjectileRenderer::new);
     }

     @SubscribeEvent
     public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
          var particleEngine = Minecraft.getInstance().particleEngine;
          particleEngine.register(CgsParticles.BLUE_FLAME.get(), FlameParticle.Provider::new);
     }
}
