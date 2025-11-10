package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.data.constants.Animations;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import software.bernie.geckolib.core.animation.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static software.bernie.geckolib.core.animation.Animation.LoopType.*;
import static software.bernie.geckolib.core.animation.RawAnimation.begin;

public class RevolverAnimator extends WeaponAnimator {
    public static final String BELT = "belt";

    private boolean hasBelt;
    private boolean isAuto;
    private boolean oneHanded = false;

    protected final AnimationController<WeaponAnimator> BELT_CONTROLLER = createController("belt_controller", animateBelt())
            .triggerableAnim(BELT, begin().then(BELT, PLAY_ONCE));

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
//        controllerRegistrar.add(BELT_CONTROLLER);
    }

    @Override
    public void tick(TickEvent event) {
        super.tick(event);
        if (event.phase == TickEvent.Phase.START) {
            if (isGun()) {
                var chamberAttachment = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.CHAMBER, getStack());
                var frame = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.FRAME, getStack());
                var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, getStack());

                this.hasBelt = chamberAttachment.is(CgsAttachments.REVOLVER_BELT.get());
                this.isAuto = frame.is(CgsAttachments.REVOLVER_AUTO.get());
                this.oneHanded = barrel.isEmpty();
                oneHanded = WeaponModifierHelper.isOneHanded(getGunData());
            }
        }
    }

    private AnimationController.AnimationStateHandler<WeaponAnimator> animateBelt() {
        return (event) ->{
            if(hasBelt && shootingHandler.isOnCooldown(getEntity(), getArm())) {
                var animation = begin()
                        .then(BELT, PLAY_ONCE)
                        .then("void", PLAY_ONCE);

                return event.setAndContinue(animation);
            }

            return event.setAndContinue(begin().then("void", PLAY_ONCE));
        };
    }

    public RevolverAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
        super(transformType, renderer);
    }

    protected String getGunAnim(String name) {
        if (oneHanded && animationHelper.hasAnimation(name + Animations.ONE_HAND_SUFFIX)) {
            name = name + Animations.ONE_HAND_SUFFIX;
        }

        return name;
    }

//
//    @Override
//    protected RawAnimation playGunAnim(String name, Animation.LoopType loopType) {
//        if (oneHanded && animationHelper.hasAnimation(name + Animations.ONE_HAND_SUFFIX))
//            return begin().then(name + Animations.ONE_HAND_SUFFIX, loopType);
//        return begin().then(name, loopType);
//    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<WeaponAnimator> event) {
        if(isAuto){
            return playGunAnim("hold_auto", LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<WeaponAnimator> event) {
        if(isAuto){
            var animation = playGunAnim("shot_auto", LOOP);
            animationHelper.syncAnimation(event, "shot_auto", rate);
            return animation;
        }
        return super.getShootingAnimation(event);
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<WeaponAnimator> event) {
        if(hasBelt){
            var animation = begin();
            animation.then("reload_belt", LOOP);
            var data = new WeaponData(getStack(), getEntity());
            animationHelper.syncAnimation(event, "reload_belt", WeaponModifierHelper.getReloadTime(data));

            return animation;
        }
        return super.getReloadingAnimation(event);
    }

    private boolean isGun() {
        return getStack().getItem() instanceof WeaponItem;
    }
}
