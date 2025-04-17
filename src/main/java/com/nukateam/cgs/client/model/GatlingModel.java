package com.nukateam.cgs.client.model;

import com.nukateam.cgs.client.animators.GatlingAnimator;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import mod.azure.azurelib.core.animation.AnimationState;

public class GatlingModel extends GeoGunModel<GatlingAnimator> {
    @Override
    public void setCustomAnimations(GatlingAnimator animatable, long instanceId, AnimationState<GatlingAnimator> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
//        var barrels = this.getAnimationProcessor().getBone("barrel_group");
//
//        if (barrels != null && animatable.getTransformType() == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
//            float rot = Mth.lerp(Minecraft.getInstance().getFrameTime(), animatable.prog0, animatable.prog);
//            Gunsmithing.LOGGER.info(String.valueOf(rot));
//            barrels.setRotZ(rot);
//        }
    }
}
