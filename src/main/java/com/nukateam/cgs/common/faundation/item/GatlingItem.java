package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.ntgl.modifiers.GatlingModifier;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.GatlingAnimator;
import com.nukateam.cgs.client.render.GatlingRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class GatlingItem extends BaseGunItem {
    private final Lazy<GatlingRenderer> RENDERER = Lazy.of(() -> new GatlingRenderer());

    public GatlingItem(Properties properties) {
        super(properties);
        modifierFactory = GatlingModifier::new;
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