package com.nukateam.cgs.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.GatlingAnimator;
import com.nukateam.cgs.client.render.GatlingRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.function.BiFunction;

public class GatlingItem extends BaseGunItem {
    private static final GatlingRenderer RENDERER = new GatlingRenderer();

    public GatlingItem(Properties properties) {
        super(properties);
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER;
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return GatlingAnimator::new;
    }
}