package com.nukateam.cgs.client.renderers.weapon;

import com.nukateam.cgs.client.layers.HammerHeadLayer;

public class HammerRenderer extends BaseWeaponRenderer {
    public HammerRenderer() {
        super();
        addRenderLayer(new HammerHeadLayer<>(this));
    }
}
