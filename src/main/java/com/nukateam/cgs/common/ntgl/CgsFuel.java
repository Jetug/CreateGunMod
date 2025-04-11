package com.nukateam.cgs.common.ntgl;
import com.nukateam.ntgl.common.base.holders.SecondaryAmmoType;
import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsFuel {
    public static SecondaryAmmoType AIR = new SecondaryAmmoType(cgsResource("air"));

    static {
        SecondaryAmmoType.registerType(AIR);
    }
}
