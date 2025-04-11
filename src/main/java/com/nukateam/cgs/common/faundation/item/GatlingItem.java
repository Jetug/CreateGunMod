package com.nukateam.cgs.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.GatlingAnimator;
import com.nukateam.cgs.client.render.GatlingRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class GatlingItem extends CgsGunItem {
    private final Lazy<GatlingRenderer> RENDERER = Lazy.of(() -> new GatlingRenderer());

    public GatlingItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return GatlingAnimator::new;
    }
}