package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunStateHelper;

public class HammerModifier implements IGunModifier {
    @Override
    public float modifyMeleeDamage(float damage, GunData data) {
        if (data.gun != null && GunStateHelper.hasAttachmentEquipped(data.gun, CgsAttachmentTypes.HEAD)) {
            if(GunStateHelper.hasAmmo(data.gun)){
                damage += 6;
            }

            return damage;
        } else {
            return 1;
        }
    }
}
