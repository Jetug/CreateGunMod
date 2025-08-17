package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.AmmoType;
import com.nukateam.ntgl.common.data.holders.AttachmentType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAmmoType {
    public static AmmoType AIR = new AmmoType(cgsResource("air"));
    public static AmmoType NAIL = new AmmoType(cgsResource("nail"));
    public static AmmoType ROCKET = new AmmoType(cgsResource("rocket"));
    public static AmmoType SMALL_ROCKET = new AmmoType(cgsResource("small_rocket"));

    public static void register(){
        AmmoType.registerType(AIR);
        AmmoType.registerType(NAIL);
        AmmoType.registerType(ROCKET);
        AmmoType.registerType(SMALL_ROCKET);
    }
}
