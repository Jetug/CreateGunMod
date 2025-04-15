package com.nukateam.cgs.client;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import net.minecraft.world.item.ItemDisplayContext;

public class NailgunAnimator extends EngineAnimator {
    public NailgunAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }
}
