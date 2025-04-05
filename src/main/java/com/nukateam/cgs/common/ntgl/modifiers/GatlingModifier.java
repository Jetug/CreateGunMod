package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.util.handler.AimingHandler;
import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.base.GunModifiers;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.base.holders.GripType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

public class GatlingModifier extends DynamicGunModifier {
    @Override
    public GripType modifyGripType(GripType gripType) {
        var gun = GunModifierHelper.getGun(getStack());
        var hasEngine = Gun.hasAttachmentEquipped(getStack(), gun, CgsAttachmentTypes.ENGINE);
        var hasDrum = Gun.getAttachmentItem(AttachmentType.MAGAZINE, getStack()).getItem() == AttachmentItems.GATLING_DRUM.get();

        if(hasEngine && !hasDrum && getEntity().hasEffect(MobEffects.DAMAGE_BOOST)){
            return GripType.ONE_HANDED;
        }
        return super.modifyGripType(gripType);
    }
}
