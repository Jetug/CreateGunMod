package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import net.minecraft.world.item.ItemStack;

import static com.nukateam.cgs.common.faundation.registry.AttachmentItems.*;

public class ShotgunModifier extends DynamicGunModifier {
    @Override
    public int modifyFireRate(int rate) {
        var attachment = Gun.getAttachmentItem(AttachmentType.MAGAZINE, stack);
        if(!attachment.isEmpty() && !isAmmoEven(stack)) {
            var item = attachment.getItem();
            var hasDrums = item == SHOTGUN_DRUM.get();
            var hasPumps = item == SHOTGUN_PUMP.get();

            if(hasDrums || hasPumps){
                return 20;
            }
        }
        return super.modifyFireRate(rate);
    }

    public static boolean isAmmoEven(ItemStack stack) {
        return Gun.getAmmo(stack) % 2 == 0;
    }
}
