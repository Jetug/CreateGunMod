package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.client.*;
import com.nukateam.geo.render.*;
import com.nukateam.ntgl.client.animators.*;
import net.minecraft.world.item.*;

import java.util.function.BiFunction;

public class FlintlockItem extends BaseGunItem {
    public FlintlockItem(Properties properties) {
        super(properties);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGeoItemRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return FlintlockAnimator::new;
    }
}