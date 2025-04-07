package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.client.GatlingAnimator;
import com.nukateam.cgs.client.RevolverAnimator;
import com.nukateam.cgs.client.render.GatlingRenderer;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class RevolverItem extends BaseGunItem {
    public RevolverItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return RevolverAnimator::new;
    }
}