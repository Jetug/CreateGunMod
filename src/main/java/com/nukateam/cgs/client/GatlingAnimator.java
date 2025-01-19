package com.nukateam.cgs.client;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.ClientHandler;
import com.nukateam.ntgl.client.util.handler.ShootingData;
import com.nukateam.ntgl.client.util.handler.ShootingHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isRightHand;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends GunAnimator {
    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected int getBarrelAmount(){
        return 4;
    }

    @Override
    public void tick() {
        super.tick();
        var fireTimer = GunModifierHelper.getFireDelay(getStack());
        var data = ShootingHandler.get().getShootingData(TransformUtils.getHand(transformType));
        if(fireTimer == data.fireTimer){
            barrelCycler.cycle();
        }
        Gunsmithing.LOGGER.info(String.valueOf(barrelCycler.getCurrent()));
    }

    @Override
    protected RawAnimation getChargingAnimation(AnimationState<GunAnimator> event , ShootingData data) {
        var animation = begin();
        var fireTimer = GunModifierHelper.getFireDelay(getStack());

        if(animationHelper.hasAnimation(CHARGE)) {
            BARREL_CONTROLLER.stop();
            BARREL_CONTROLLER.setAnimation(begin().then("void", PLAY_ONCE));
            animationHelper.syncAnimation(event, CHARGE, fireTimer);
            animation = playGunAnim(CHARGE, LOOP);
        }
        return animation;
    }
}
