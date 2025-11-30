package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.WeaponRenderers;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.animators.GatlingAnimator;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiFunction;

public class GatlingItem extends CgsGunItem {
    public GatlingItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return WeaponRenderers.GATLING_RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return GatlingAnimator::new;
    }
}