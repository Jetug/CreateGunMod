package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

public class LauncherItem extends CgsGunItem {
    public LauncherItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return LauncherAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var magazineAttachment = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, stack).getItem();
        if(magazineAttachment == CgsAttachments.BALLISTAZOOKA.get()){
            return "item.cgs.ballistazooka";
        }

        return super.getDescriptionId(stack);
    }
}