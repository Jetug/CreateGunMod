package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.projectile.CgsProjectileRenderer;
import com.nukateam.cgs.client.renderers.projectile.FireballRenderer;
import com.nukateam.cgs.client.renderers.weapon.BaseWeaponRenderer;
import com.nukateam.cgs.client.renderers.weapon.FlintlockRenderer;
import com.nukateam.cgs.client.renderers.weapon.GatlingRenderer;
import com.nukateam.cgs.client.renderers.weapon.HammerRenderer;
import com.nukateam.cgs.common.faundation.registry.CgsParticles;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.ntgl.CgsProjectileRegistry;
import com.nukateam.ntgl.client.registry.WeaponRegistry;
import com.nukateam.ntgl.client.render.renderers.projectiles.ProjectileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
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

     @SubscribeEvent
     public static void onCommonSetup(FMLClientSetupEvent event) {
          event.enqueueWork(CgsProjectileRegistry::registerProjectiles);

          WeaponRegistry.registerRenderer(CgsWeapons.FLINTLOCK.get(), new FlintlockRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.REVOLVER .get(), new BaseWeaponRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.SHOTGUN  .get(), new BaseWeaponRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.NAILGUN  .get(), new BaseWeaponRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.GATLING  .get(), new GatlingRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.BLAZEGUN .get(), new BaseWeaponRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.LAUNCHER .get(), new BaseWeaponRenderer());
          WeaponRegistry.registerRenderer(CgsWeapons.HAMMER   .get(), new HammerRenderer());

          WeaponRegistry.registerAnimator(CgsWeapons.FLINTLOCK.get(), FlintlockAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.REVOLVER.get(), RevolverAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.SHOTGUN .get(), ShotgunAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.NAILGUN .get(), NailgunAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.GATLING .get(), GatlingAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.BLAZEGUN.get(), BlazegunAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.LAUNCHER.get(), LauncherAnimator::new);
          WeaponRegistry.registerAnimator(CgsWeapons.HAMMER  .get(), HammerAnimator::new);

     }
}
