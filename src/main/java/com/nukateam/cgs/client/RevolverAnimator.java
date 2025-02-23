package com.nukateam.cgs.client;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.data.constants.Animations;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static com.nukateam.example.common.util.constants.Animations.HOLD;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class RevolverAnimator extends GunAnimator {
    private boolean hasBelt;
    private boolean isAuto;

//    protected final AnimationController<GunAnimator> BELT_CONTROLLER = createController("belt_controller", animateBelt());
//
//    private AnimationController.AnimationStateHandler<GunAnimator> animateBelt() {
//
//    }

    public RevolverAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected AnimationController.AnimationStateHandler<GunAnimator> animateRevolver() {
        if(hasBelt){
            return (event) ->{
                var animation = begin()
                        .then("belt", PLAY_ONCE)
                        .then("void", PLAY_ONCE);

                animationHelper.syncAnimation(event, "belt", rate);
                return event.setAndContinue(animation);
            };
        }
        else return super.animateRevolver();
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
            var rate = GunModifierHelper.getRate(getStack());
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
            animationHelper.syncAnimation(event, "reload_belt", GunModifierHelper.getReloadTime(getStack()));

            return animation;
        }
        return super.getReloadingAnimation(event);
    }

    @Override
    public void tick(TickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (isGun()) {
                var chamberAttachment = Gun.getAttachmentItem(CgsAttachmentTypes.CHAMBER, getStack());
                var frame = Gun.getAttachmentItem(CgsAttachmentTypes.FRAME, getStack());

                this.hasBelt = chamberAttachment.is(AttachmentItems.BELT.get());
                this.isAuto = frame.is(AttachmentItems.AUTO.get());
            }
        }
    }

    private boolean isGun() {
        return getStack().getItem() instanceof GunItem;
    }
}
