package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.client.*;
import com.nukateam.cgs.common.ntgl.modifiers.*;
import com.nukateam.geo.render.*;
import com.nukateam.ntgl.client.animators.*;
import net.minecraft.world.item.*;
import java.util.function.*;

public class ShotgunItem extends BaseGunItem {
    public ShotgunItem(Properties properties) {
        super(ShotgunModifier::new, properties);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return ShotgunAnimator::new;
    }
}