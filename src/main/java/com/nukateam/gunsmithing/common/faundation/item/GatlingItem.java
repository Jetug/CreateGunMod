package com.nukateam.gunsmithing.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.gunsmithing.client.GatlingAnimator;
import com.nukateam.gunsmithing.client.render.BaseGunRenderer;
import com.nukateam.gunsmithing.client.render.GatlingRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

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