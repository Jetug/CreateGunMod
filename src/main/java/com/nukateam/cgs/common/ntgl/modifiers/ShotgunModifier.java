package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.ModItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;

public class ShotgunModifier extends DynamicGunModifier {
    @Override
    public int modifyFireRate(int rate) {
        var ammo = Gun.getAmmo(stack);
        var attachment = Gun.getAttachmentItem(AttachmentType.MAGAZINE, stack);
        if(!attachment.isEmpty() && ammo % 2 != 0 ) {
            if(attachment.getItem() == AttachmentItems.SHOTGUN_DRUM.get()){
                return 20;
            }
        }
        return super.modifyFireRate(rate);
    }
}
