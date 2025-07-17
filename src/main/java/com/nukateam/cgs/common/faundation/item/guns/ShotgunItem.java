package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.ShotgunAnimator;
import com.nukateam.geo.render.*;
import com.nukateam.ntgl.client.animators.*;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.*;
import java.util.function.*;

public class ShotgunItem extends CgsGunItem {
    public ShotgunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return ShotgunAnimator::new;
    }
}