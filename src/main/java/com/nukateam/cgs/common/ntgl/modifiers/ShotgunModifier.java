package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.ntgl.client.util.handler.ShootingHandler;
import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import net.minecraft.world.item.ItemStack;

public class ShotgunModifier extends DynamicGunModifier {
    @Override
    public int modifyFireRate(int rate) {
        var attachment = Gun.getAttachmentItem(AttachmentType.MAGAZINE, stack);
        if(!attachment.isEmpty() && !isAmmoEven(stack)) {
            if(attachment.getItem() == AttachmentItems.SHOTGUN_DRUM.get()){
                return 20;
            }
        }
        return super.modifyFireRate(rate);
    }

    public static boolean isAmmoEven(ItemStack stack) {
        var ammo = Gun.getAmmo(stack);
        return ammo % 2 == 0;
    }
}
