package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

public class LauncherItem extends CgsGunItem {
    public LauncherItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return LauncherAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var magazineAttachment = GunStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, stack).getItem();
        if(magazineAttachment == AttachmentItems.BALLISTAZOOKA.get()){
            return "item.cgs.ballistazooka";
        }

        return super.getDescriptionId(stack);
    }
}