package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.entity.BlazeProjectile;
import com.nukateam.cgs.common.faundation.entity.GeoProjectile;
import com.nukateam.cgs.common.faundation.entity.NailProjectile;
import com.nukateam.cgs.common.faundation.entity.RocketProjectile;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import com.nukateam.ntgl.common.util.interfaces.IProjectileFactory;
import com.nukateam.ntgl.common.util.managers.ProjectileManager;

public class CgsProjectileRegistry {
    private static final IProjectileFactory FIREBALL = (level, data) ->
            new BlazeProjectile(CgsProjectiles.FIREBALL.get(), level, data);

    private static final IProjectileFactory ROCKET = (level, data) ->
            new RocketProjectile(CgsProjectiles.ROCKET.get(), level, data);

    private static final IProjectileFactory SMALL_ROCKET = (level, data) ->
            new RocketProjectile(CgsProjectiles.SMALL_ROCKET.get(), level, data);

    private static final IProjectileFactory NAIL = (level, data) ->
            new NailProjectile(CgsProjectiles.NAIL.get(), level, data);

    private static final IProjectileFactory SPEAR = (level, data) ->
            new GeoProjectile(CgsProjectiles.SPEAR.get(), level, data);

    public static void registerProjectiles() {
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.FIREBALL, FIREBALL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.ROCKET, ROCKET);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.SMALL_ROCKET, SMALL_ROCKET);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.NAIL, NAIL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.SPEAR, SPEAR);
    }
}
