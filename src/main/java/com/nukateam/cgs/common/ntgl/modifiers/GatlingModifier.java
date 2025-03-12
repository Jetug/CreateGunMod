package com.nukateam.cgs.common.ntgl.modifiers;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.base.GunModifiers;
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

        if(hasEngine && getEntity().hasEffect(MobEffects.DAMAGE_BOOST)){
            return GripType.ONE_HANDED;
        }
        return super.modifyGripType(gripType);
    }
}
