package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunStateHelper;

public class HammerModifier implements IGunModifier {
    @Override
    public float modifyDamage(float damage, GunData data) {
        var attachment = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, data.gun);
        if(!attachment.isEmpty()){
            var headItem = (HammerHeadItem)attachment.getItem();
            return damage + headItem.getTier().getAttackDamageBonus() * 2;
        }

        return IGunModifier.super.modifyDamage(damage, data);
    }
}
