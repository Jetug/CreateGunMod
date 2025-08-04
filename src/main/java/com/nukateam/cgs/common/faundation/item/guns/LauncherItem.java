package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.ntgl.common.util.interfaces.IGunModifier;

public class LauncherItem extends CgsGunItem {
//    private final Lazy<GatlingRenderer> RENDERER = Lazy.of(() -> new GatlingRenderer());

    public LauncherItem(Properties properties, IGunModifier... modifiers) {
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