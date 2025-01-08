package com.nukateam.gunsmithing.client.model;

import com.nukateam.gunsmithing.Gunsmithing;
import com.nukateam.gunsmithing.client.GatlingAnimator;
import com.nukateam.gunsmithing.client.render.GatlingRenderer;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

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
