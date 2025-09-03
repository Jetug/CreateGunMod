package com.nukateam.cgs.client.renderers;

import com.nukateam.cgs.client.layers.HammerHeadLayer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.layers.GlowingLayer;

public class HammerRenderer extends BaseGunRenderer{
    public HammerRenderer() {
        super();
        addRenderLayer(new HammerHeadLayer<>(this));
    }
}
