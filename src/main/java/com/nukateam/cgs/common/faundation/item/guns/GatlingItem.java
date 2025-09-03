package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.animators.GatlingAnimator;
import com.nukateam.cgs.client.renderers.GatlingRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class GatlingItem extends CgsGunItem {
    private final Lazy<GatlingRenderer> RENDERER = Lazy.of(() -> new GatlingRenderer());

    public GatlingItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return GatlingAnimator::new;
    }
}