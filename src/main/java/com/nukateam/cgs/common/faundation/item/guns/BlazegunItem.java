package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.renderers.GatlingRenderer;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraftforge.common.util.Lazy;

public class BlazegunItem extends CgsGunItem {
    private final Lazy<GatlingRenderer> RENDERER = Lazy.of(() -> new GatlingRenderer());

    public BlazegunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

//    @Override
//    public DynamicGeoItemRenderer getRenderer() {
//        return RENDERER.get();
//    }

//    @Override
//    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
//        return GatlingAnimator::new;
//    }
}