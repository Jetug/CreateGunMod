package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.entity.BlazeProjectile;
import com.nukateam.cgs.common.faundation.entity.GeoProjectile;
import com.nukateam.cgs.common.faundation.entity.NailProjectile;
import com.nukateam.cgs.common.faundation.entity.RocketProjectile;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import com.nukateam.ntgl.common.util.interfaces.IProjectileFactory;
import com.nukateam.ntgl.common.util.managers.ProjectileManager;

public class CgsProjectileRegistry {
    private static final IProjectileFactory FIREBALL = (level, entity, weapon, item, modifiedGun) ->
            new BlazeProjectile(CgsProjectiles.FIREBALL.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory ROCKET = (level, entity, weapon, item, modifiedGun) ->
            new RocketProjectile(CgsProjectiles.ROCKET.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory SMALL_ROCKET = (level, entity, weapon, item, modifiedGun) ->
            new RocketProjectile(CgsProjectiles.SMALL_ROCKET.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory NAIL = (level, entity, weapon, item, modifiedGun) ->
            new NailProjectile(CgsProjectiles.NAIL.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory SPEAR = (level, entity, weapon, item, modifiedGun) ->
            new GeoProjectile(CgsProjectiles.SPEAR.get(), level, entity, weapon, item, modifiedGun);

    public static void registerProjectiles() {
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.FIREBALL, FIREBALL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.ROCKET, ROCKET);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.SMALL_ROCKET, SMALL_ROCKET);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.NAIL, NAIL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.SPEAR, SPEAR);
    }
}
