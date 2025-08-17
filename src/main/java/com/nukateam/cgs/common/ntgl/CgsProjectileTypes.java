package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.ProjectileType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsProjectileTypes {
    public static ProjectileType FIREBALL = new ProjectileType(cgsResource("fireball"));
    public static ProjectileType ROCKET = new ProjectileType(cgsResource("rocket"));
    public static ProjectileType NAIL = new ProjectileType(cgsResource("nail"));

    static {
        ProjectileType.registerType(FIREBALL);
        ProjectileType.registerType(ROCKET);
        ProjectileType.registerType(NAIL);
    }
}
