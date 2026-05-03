package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.WeaponRenderers;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.weapon.HammerRenderer;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.BiFunction;

public class HammerItem extends CgsGunItem {
    public HammerItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(WeaponData data){
        var ammoPerShot = WeaponModifierHelper.getAmmoPerShot(data);
        return WeaponStateHelper.isAmmoIgnored(data) || WeaponStateHelper.getAmmoCount(data) >= ammoPerShot;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return WeaponRenderers.HAMMER_RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return HammerAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var headAttachment = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, new WeaponData(stack, Minecraft.getInstance().player)).getItem();

        if(headAttachment instanceof HammerHeadItem item && item.getHeadType() == HammerHeadItem.Type.AXE){
            return "item.cgs.axe";
        }

        return super.getDescriptionId(stack);
    }
}