package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.ntgl.client.animators.*;
import com.nukateam.cgs.client.animators.*;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.function.BiFunction;

public class BlazegunItem extends CgsGunItem {
    public BlazegunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return BlazegunAnimator::new;
    }
}