package com.nukateam.cgs.client.model;

import com.nukateam.cgs.client.animators.GatlingAnimator;
import com.nukateam.ntgl.client.model.gun.GeoWeaponModel;
import com.nukateam.ntgl.client.model.gun.GeoWeaponModel;
import software.bernie.geckolib.core.animation.AnimationState;

public class GatlingModel extends GeoWeaponModel<GatlingAnimator> {
    @Override
    public void setCustomAnimations(GatlingAnimator animatable, long instanceId, AnimationState<GatlingAnimator> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
