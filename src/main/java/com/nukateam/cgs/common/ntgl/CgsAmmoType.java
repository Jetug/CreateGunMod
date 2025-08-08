package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.AmmoType;
import com.nukateam.ntgl.common.data.holders.AttachmentType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAmmoType {
    public static AmmoType NAIL = new AmmoType(cgsResource("nail"));

    static {
        AmmoType.registerType(NAIL);
    }
}
