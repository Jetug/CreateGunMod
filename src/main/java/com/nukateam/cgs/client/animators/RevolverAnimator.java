package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.helpers.PlayerHelper;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import com.nukateam.ntgl.common.data.constants.Animations;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraftforge.event.TickEvent;

import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class RevolverAnimator extends GunAnimator {
    public static final String BELT = "belt";

    private boolean hasBelt;
    private boolean isAuto;
    private boolean oneHanded = false;

    protected final AnimationController<GunAnimator> BELT_CONTROLLER = createController("belt_controller", animateBelt())
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
                var chamberAttachment = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.CHAMBER, getStack());
                var frame = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.FRAME, getStack());
                var barrel = GunStateHelper.getAttachmentItem(AttachmentType.BARREL, getStack());

                this.hasBelt = chamberAttachment.is(AttachmentItems.REVOLVER_BELT.get());
                this.isAuto = frame.is(AttachmentItems.REVOLVER_AUTO.get());
                this.oneHanded = barrel.isEmpty();
                oneHanded = GunStateHelper.isOneHanded(getGunData());
            }
        }
    }

    private AnimationController.AnimationStateHandler<GunAnimator> animateBelt() {
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

    public RevolverAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
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
            animationHelper.syncAnimation(event, "shot_auto", rate);
            return animation;
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

    private boolean isGun() {
        return getStack().getItem() instanceof WeaponItem;
    }
}
