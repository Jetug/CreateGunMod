package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.RevolverAnimator;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.function.BiFunction;

public class RevolverItem extends CgsGunItem {
    public RevolverItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return RevolverAnimator::new;
    }
}