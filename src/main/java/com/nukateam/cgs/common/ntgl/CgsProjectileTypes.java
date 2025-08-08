package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.ProjectileType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsProjectileTypes {
    public static ProjectileType FIREBALL = new ProjectileType(cgsResource("fireball"));
    static {
        ProjectileType.registerType(FIREBALL);
    }
}
