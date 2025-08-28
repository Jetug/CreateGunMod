package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.renderers.GatlingRenderer;
import com.nukateam.cgs.client.renderers.HammerRenderer;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class HammerItem extends CgsGunItem {
    private final Lazy<HammerRenderer> RENDERER = Lazy.of(HammerRenderer::new);

    public HammerItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(GunData data){
        return GunStateHelper.getAmmoCount(data) > 0;
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return HammerAnimator::new;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        var magazineAttachment = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, stack).getItem();
        if(magazineAttachment instanceof HammerHeadItem item && item.getHeadType() == HammerHeadItem.Type.AXE){
            return "item.cgs.axe";
        }

        return super.getDescriptionId(stack);
    }
}