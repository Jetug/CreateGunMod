package com.nukateam.cgs.client.animators;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class NailgunAnimator extends EngineAnimator {
    public NailgunAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }
}
