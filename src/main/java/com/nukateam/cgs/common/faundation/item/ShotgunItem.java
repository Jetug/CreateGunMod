package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.client.*;
import com.nukateam.geo.render.*;
import com.nukateam.ntgl.client.animators.*;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.*;
import java.util.function.*;

public class ShotgunItem extends CgsGunItem {
    public ShotgunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return ShotgunAnimator::new;
    }
}