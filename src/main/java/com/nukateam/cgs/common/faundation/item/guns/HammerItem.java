package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.*;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.constants.Tags;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiFunction;

public class HammerItem extends CgsGunItem {
    public HammerItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(WeaponData data){
        var tag = data.weapon.getOrCreateTag();
        var ammoPerShot = WeaponModifierHelper.getAmmoPerShot(data);
        return tag.getBoolean("IgnoreAmmo") || WeaponStateHelper.getAmmoCount(data) >= ammoPerShot;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return Renderers.HAMMER_RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return HammerAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var headAttachment = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, stack).getItem();
        if(headAttachment instanceof HammerHeadItem item && item.getHeadType() == HammerHeadItem.Type.AXE){
            return "item.cgs.axe";
        }

        return super.getDescriptionId(stack);
    }
}