package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;

import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class BlazegunAnimator extends GunAnimator {
    public static final String SHOT_AUTO = "shot_auto";
    private boolean isAuto;

    public BlazegunAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }
    @Override
    protected void tickStart() {
        super.tickStart();
        try {
            if (itemStack.getItem() instanceof WeaponItem) {
                this.isAuto = GunStateHelper.hasAttachmentEquipped(getStack(), CgsAttachmentTypes.ENGINE);
            }
        } catch (IllegalStateException e) {
            Gunsmithing.LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<GunAnimator> event) {
        if(isAuto){
            var animation = begin().then(getGunAnim(SHOT_AUTO), LOOP);
            animationHelper.syncAnimation(event, rate, SHOT_AUTO);
            return animation;
        }
        return super.getShootingAnimation(event);
    }
}
