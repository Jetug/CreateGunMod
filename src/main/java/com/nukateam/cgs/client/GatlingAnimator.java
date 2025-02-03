package com.nukateam.cgs.client;

import com.nukateam.cgs.common.faundation.registry.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.ClientHandler;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.example.common.util.constants.Animations.*;
import static com.nukateam.ntgl.client.util.util.TransformUtils.isFirstPerson;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends GunAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    protected final AnimationController<GunAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());
    protected final AnimationController<GunAnimator> GATLING_TRIGGER = createController( "gatling_trigger", event -> PlayState.CONTINUE)
            .triggerableAnim(HANDLE, begin().then(HANDLE, PLAY_ONCE))
            .triggerableAnim(VOID, begin().then(VOID, PLAY_ONCE));

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateHandle() {
        return (event) -> {
            var gun = ((GunItem)getStack().getItem()).getGun();

            var animation = begin();

            if (Gun.hasAttachmentEquipped(getStack(), gun, CgsAttachmentTypes.ENGINE)
                    || MAIN_CONTROLLER.getCurrentAnimation().animation().name().equals(RELOAD)) {
                return PlayState.STOP;
            } else {
                var reloadHandler = ClientReloadHandler.get();

                if (reloadHandler.isReloading(getEntity(), getArm()))
                    animation = begin().then(VOID, PLAY_ONCE);
                else return getCycledAnimation(event, HANDLE, this.barrelCycler);
            }

            return event.setAndContinue(animation);

        };
//        return event -> {
//            var controller = event.getController();
//            controller.setAnimationSpeed(1);
//            var entity = getEntity();
//            var arm = getArm();
//            var isShooting = shootingHandler.isShooting(entity, arm);
//            var animation = begin();
//            var rate = GunModifierHelper.getRate(getStack());
//
//            if (isShooting) {
//                aTicks = rate * getBarrelAmount();
//                animation.then(HANDLE, LOOP);
//                animationHelper.syncAnimation(event, HANDLE, rate * getBarrelAmount());
//            }
//            else if (aTicks == 0){
//                animation.then(VOID, PLAY_ONCE);
//            }
//
//            return event.setAndContinue(animation);
//        };
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
        HANDLE_CONTROLLER.setAnimation(begin().then(VOID, PLAY_ONCE));
        return super.getReloadingAnimation(event);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(HANDLE_CONTROLLER);
        controllerRegistrar.add(GATLING_TRIGGER);
    }

    @Override
    protected int getBarrelAmount(){
        return 4;
    }

    protected int aTicks = 0;

    @Override
    public void tick() {
        super.tick();

//        var entity = getEntity();
//        var arm = getArm();
//        var isShooting = shootingHandler.isShooting(entity, arm);
//        var rate = GunModifierHelper.getRate(getStack());

        aTicks = Math.max(aTicks - 1, 0);

//        if(isShooting && !GATLING_TRIGGER.isPlayingTriggeredAnimation()){
//            var ratio = animationHelper.getSpeedMultiplier(HANDLE, rate * getBarrelAmount() - 3);
//            GATLING_TRIGGER.setAnimationSpeed(ratio);
//            GATLING_TRIGGER.tryTriggerAnimation(HANDLE);
//        }
//        else if(!isShooting){
//            GATLING_TRIGGER.tryTriggerAnimation("void");
//        }
    }
}
