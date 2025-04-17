package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.data.constants.Animations;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class RevolverAnimator extends GunAnimator {
    public static final String BELT = "belt";

    private boolean hasBelt;
    private boolean isAuto;
    private boolean oneHanded = true;

    protected final AnimationController<GunAnimator> BELT_CONTROLLER = createController("belt_controller", animateBelt())
            .triggerableAnim(BELT, begin().then(BELT, PLAY_ONCE));

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
//        controllerRegistrar.add(BELT_CONTROLLER);
    }

    private AnimationController.AnimationStateHandler<GunAnimator> animateBelt() {
        return (event) ->{
            if(hasBelt && shootingHandler.isOnCooldown(getEntity(), getArm())) {
                var animation = begin()
                        .then(BELT, PLAY_ONCE)
                        .then("void", PLAY_ONCE);

//                animationHelper.syncAnimation(event, BELT, rate);
                return event.setAndContinue(animation);
            }

            return event.setAndContinue(begin().then("void", PLAY_ONCE));
        };
    }

    public RevolverAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation playGunAnim(String name, Animation.LoopType loopType) {
        if (oneHanded && animationHelper.hasAnimation(name + Animations.ONE_HAND_SUFFIX))
            return begin().then(name + Animations.ONE_HAND_SUFFIX, loopType);
        return begin().then(name, loopType);
    }

    //    @Override
//    protected AnimationController.AnimationStateHandler<GunAnimator> animateRevolver() {
//        if(hasBelt){
//            return (event) ->{
//                var animation = begin()
//                        .then("belt", PLAY_ONCE)
//                        .then("void", PLAY_ONCE);
//
//                animationHelper.syncAnimation(event, "belt", rate);
//                return event.setAndContinue(animation);
//            };
//        }
//        else return super.animateRevolver();
//    }


    @Override
    protected AnimationController.AnimationStateHandler<GunAnimator> animate() {
//        if(BELT_CONTROLLER != null && BELT_CONTROLLER.isPlayingTriggeredAnimation()) {
//            var multiplier = (float) animationHelper.getSpeedMultiplier(BELT, rate);
//            BELT_CONTROLLER.setAnimationSpeed(multiplier);
//        }
        return super.animate();
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(isAuto){
            return playGunAnim("hold_auto", LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<GunAnimator> event) {
        if(isAuto){
            var animation = playGunAnim("shot_auto", LOOP);
            var data = new GunData(getStack(), getEntity());
            var rate = GunModifierHelper.getRate(data);
            animationHelper.syncAnimation(event, "shot_auto", rate);
            return animation;

//            var animation = begin();
//            animation.then("shot_auto", LOOP);
//            animationHelper.syncAnimation(event, "shot_auto", GunModifierHelper.getReloadTime(getStack()));
//            return animation;
        }
        return super.getShootingAnimation(event);
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
        if(hasBelt){
            var animation = begin();
            animation.then("reload_belt", LOOP);
            var data = new GunData(getStack(), getEntity());
            animationHelper.syncAnimation(event, "reload_belt", GunModifierHelper.getReloadTime(data));

            return animation;
        }
        return super.getReloadingAnimation(event);
    }

    @Override
    public void tick(TickEvent event) {
        super.tick(event);
        if (event.phase == TickEvent.Phase.START) {
            if (isGun()) {
//                if(shootingHandler.getCooldown(getEntity(), getArm()) == rate){
//                    var multiplier = (float) animationHelper.getSpeedMultiplier(BELT, rate);
//                    BELT_CONTROLLER.setAnimationSpeed(multiplier);
//                    BELT_CONTROLLER.tryTriggerAnimation(BELT);
//                }

                var chamberAttachment = Gun.getAttachmentItem(CgsAttachmentTypes.CHAMBER, getStack());
                var frame = Gun.getAttachmentItem(CgsAttachmentTypes.FRAME, getStack());
                var barrel = Gun.getAttachmentItem(AttachmentType.BARREL, getStack());

                this.hasBelt = chamberAttachment.is(AttachmentItems.BELT.get());
                this.isAuto = frame.is(AttachmentItems.AUTO.get());
                this.oneHanded = barrel.isEmpty();
            }
        }
    }

    private boolean isGun() {
        return getStack().getItem() instanceof GunItem;
    }
}
