package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.*;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class HammerItem extends CgsGunItem {

    public HammerItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(GunData data){
        return GunStateHelper.getAmmoCount(data) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return Renderers.HAMMER_RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return HammerAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var headAttachment = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, stack).getItem();
        if(headAttachment instanceof HammerHeadItem item && item.getHeadType() == HammerHeadItem.Type.AXE){
            return "item.cgs.axe";
        }

        return super.getDescriptionId(stack);
    }
}