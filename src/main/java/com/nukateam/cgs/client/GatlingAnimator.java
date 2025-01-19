package com.nukateam.cgs.client;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.ClientHandler;
import com.nukateam.ntgl.client.util.handler.ShootingData;
import com.nukateam.ntgl.client.util.handler.ShootingHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isRightHand;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
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
//        var fireTimer = GunModifierHelper.getFireDelay(getStack());
//        var data = ShootingHandler.get().getShootingData(TransformUtils.getHand(transformType));
//        if(fireTimer == data.fireTimer){
//            barrelCycler.cycle();
//        }
//        Gunsmithing.LOGGER.info("barrel id: " + barrelCycler.getCurrent());
    }



    @Override
    protected RawAnimation getChargingAnimation(AnimationState<GunAnimator> event , ShootingData data) {
        var animation = begin();
        var fireTimer = GunModifierHelper.getFireDelay(getStack());
        var finalAnim = BARREL + barrelCycler.getCurrent();

        if(animationHelper.hasAnimation(finalAnim)) {
//            BARREL_CONTROLLER.stop();
//            BARREL_CONTROLLER.setAnimation(begin().then("void", PLAY_ONCE));
            animationHelper.syncAnimation(event, finalAnim, fireTimer);
            animation = playGunAnim(finalAnim, HOLD_ON_LAST_FRAME);
        }
        return animation;
    }

    @Override
    protected AnimationController.AnimationStateHandler<GunAnimator> animateBarrels() {
        return this::getCycledAnimation;

    }

    private PlayState getCycledAnimation(AnimationState<GunAnimator> event) {
        event.getController().setAnimationSpeed(1.0);
        var animation = begin();
        var fireTimer = GunModifierHelper.getFireDelay(getStack());
        var finalAnim = BARREL + barrelCycler.getCurrent();
        var shootingHandler = ShootingHandler.get();
        var arm = isRightHand(transformType) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        var data = shootingHandler.getShootingData(arm);

        if(fireTimer > 0 && data.fireTimer > 0 && fireTimer != data.fireTimer){
            if (TransformUtils.isHandTransform(this.transformType) && barrelCycler != null) {
                if(animationHelper.hasAnimation(finalAnim)) {
                    animationHelper.syncAnimation(event, finalAnim, fireTimer);
                    animation = playGunAnim(finalAnim, HOLD_ON_LAST_FRAME);
                }
                return event.setAndContinue(animation);
            }
        }


        return PlayState.STOP;
    }
}
