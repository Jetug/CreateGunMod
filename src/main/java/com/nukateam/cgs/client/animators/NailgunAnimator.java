package com.nukateam.cgs.client.animators;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class NailgunAnimator extends EngineAnimator {
    public NailgunAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
        super(transformType, renderer);
    }
}
