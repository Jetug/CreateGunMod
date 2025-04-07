package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.client.NailgunAnimator;
import com.nukateam.cgs.client.RevolverAnimator;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.function.BiFunction;

public class NailgunItem extends BaseGunItem {
    public NailgunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return NailgunAnimator::new;
    }
}