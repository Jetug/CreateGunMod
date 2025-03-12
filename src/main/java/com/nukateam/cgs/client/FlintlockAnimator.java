package com.nukateam.cgs.client;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.ClientHandler;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.data.constants.Animations;
import com.nukateam.ntgl.common.util.helpers.PlayerHelper;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;

import static com.nukateam.example.common.util.constants.Animations.HOLD;
import static com.nukateam.example.common.util.constants.Animations.SHOT;
import static com.nukateam.ntgl.client.util.util.TransformUtils.isFirstPerson;
import static com.nukateam.ntgl.client.util.util.TransformUtils.isHandTransform;
import static mod.azure.azurelib.core.animation.Animation.LoopType.HOLD_ON_LAST_FRAME;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class FlintlockAnimator extends GunAnimator {
    public static final String EMPTY = "empty";
    private boolean hasAmmo;

    public FlintlockAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }
//
//    protected AnimationController.AnimationStateHandler<GunAnimator> animate() {
//        return event -> {
//            try {
//                var controller = event.getController();
//                controller.setAnimationSpeed(1);
//                var entity = getEntity();
//                var holdAnimation = getHoldAnimation(event);
//
//                if (!isHandTransform(transformType))
//                    return event.setAndContinue(holdAnimation);
//
//                var isShooting = shootingHandler.isShooting(entity, arm);
//                var data = shootingHandler.getShootingData(arm);
//                var animation = begin();
//
//                if (fireDelay > 0 && data.fireTimer > 0 && fireDelay != data.fireTimer) {
//                    animation = getChargingAnimation(event, data);
//                } else if (reloadHandler.isReloading(entity, arm) && isFirstPerson(transformType)) {
//                    animation = getReloadingAnimation(event);
//                } else if (isShooting) {
//                    animation = getShootingAnimation(event);
//                } else if (reloadHandler.isReloading(entity, arm.getOpposite()) && isFirstPerson(transformType)) {
//                    animation = getHideAnimation();
//                } else if (ClientHandler.getInspectionTicks(getArm()) > 0) {
//                    animation = getInspectionAnimation(event);
//                } else {
//                    if (currentGun == getGunItem())
//                        animation = holdAnimation;
//                    else {
//                        currentGun = getGunItem();
//                        animation = playGunAnim(SHOT, LOOP);
//                    }
//                }
//
//                return event.setAndContinue(animation);
//            } catch (Exception e) {
//                return PlayState.STOP;
//            }
//        };
//    }
//
//    private static @NotNull RawAnimation getHideAnimation() {
//        return begin().then(Animations.HIDE, HOLD_ON_LAST_FRAME);
//    }
//
    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(!hasAmmo){
            return playGunAnim(EMPTY, LOOP);
        }
//        return super.getHoldAnimation(event);
//        return begin().then("hold", LOOP);
        return playGunAnim(HOLD, LOOP);
    }

//    protected RawAnimation playGunAnim(String name, Animation.LoopType loopType) {
//        var entity = getEntity();
//        var currentItem = entity.getItemInHand(PlayerHelper.convertHand(arm));
//        var oppositeItem = entity.getItemInHand(PlayerHelper.convertHand(arm.getOpposite()));
//        var isOneHanded = isOneHanded(currentItem) && isOneHanded(oppositeItem) || arm == HumanoidArm.LEFT;
//
//        if (isOneHanded && animationHelper.hasAnimation(name + Animations.ONE_HAND_SUFFIX))
//            return begin().then(name + Animations.ONE_HAND_SUFFIX, loopType);
//        return begin().then(name, loopType);
//    }

    @Override
    public void tick(TickEvent event) {
        super.tick(event);
        if (event.phase == TickEvent.Phase.START) {
            this.hasAmmo = Gun.hasAmmo(getStack());
        }
    }
}
