package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.ProjectileType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsProjectileTypes {
    public static ProjectileType FIREBALL = new ProjectileType(cgsResource("fireball"));
    public static ProjectileType ROCKET = new ProjectileType(cgsResource("rocket"));
    public static ProjectileType SMALL_ROCKET = new ProjectileType(cgsResource("small_rocket"));
    public static ProjectileType SPEAR = new ProjectileType(cgsResource("spear"));
    public static ProjectileType NAIL = new ProjectileType(cgsResource("nail"));
    public static ProjectileType INCENDIARY = new ProjectileType(cgsResource("incendiary"));

    static {
        ProjectileType.registerType(FIREBALL);
        ProjectileType.registerType(ROCKET);
        ProjectileType.registerType(SMALL_ROCKET);
        ProjectileType.registerType(SPEAR);
        ProjectileType.registerType(NAIL);
        ProjectileType.registerType(INCENDIARY);
    }
}
