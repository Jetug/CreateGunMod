package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.registry.ModProjectiles;
import com.nukateam.example.common.registery.ModGuns;
import com.nukateam.ntgl.common.data.holders.ProjectileType;
import com.nukateam.ntgl.common.foundation.entity.FlameProjectile;
import com.nukateam.ntgl.common.util.interfaces.IProjectileFactory;
import com.nukateam.ntgl.common.util.managers.ProjectileManager;

public class ProjectileRegistry {
    private static final IProjectileFactory FIREBALL = (level, entity, weapon, item, modifiedGun) ->
            new FlameProjectile(ModProjectiles.FIREBALL.get(), level, entity, weapon, item, modifiedGun);

    public static void registerProjectiles() {
        ProjectileManager.getInstance().registerFactory(CgsProjectileTypes.FIREBALL, FIREBALL);
    }
}
