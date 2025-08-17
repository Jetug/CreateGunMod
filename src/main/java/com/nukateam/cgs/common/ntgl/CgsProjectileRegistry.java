package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.entity.GeoProjectile;
import com.nukateam.cgs.common.faundation.entity.NailProjectile;
import com.nukateam.cgs.common.faundation.entity.RocketProjectile;
import com.nukateam.cgs.common.faundation.registry.ModProjectiles;
import com.nukateam.ntgl.common.foundation.entity.FlameProjectile;
import com.nukateam.ntgl.common.util.interfaces.IProjectileFactory;
import com.nukateam.ntgl.common.util.managers.ProjectileManager;

public class CgsProjectileRegistry {
    private static final IProjectileFactory FIREBALL = (level, entity, weapon, item, modifiedGun) ->
            new FlameProjectile(ModProjectiles.FIREBALL.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory ROCKET = (level, entity, weapon, item, modifiedGun) ->
            new RocketProjectile(ModProjectiles.ROCKET.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory NAIL = (level, entity, weapon, item, modifiedGun) ->
            new NailProjectile(ModProjectiles.NAIL.get(), level, entity, weapon, item, modifiedGun);

    private static final IProjectileFactory SPEAR = (level, entity, weapon, item, modifiedGun) ->
            new GeoProjectile(ModProjectiles.SPEAR.get(), level, entity, weapon, item, modifiedGun);

    public static void registerProjectiles() {
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.FIREBALL, FIREBALL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.ROCKET, ROCKET);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.NAIL, NAIL);
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.SPEAR, SPEAR);
    }
}
