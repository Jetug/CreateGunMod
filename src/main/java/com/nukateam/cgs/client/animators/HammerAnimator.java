package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.item.guns.HammerItem;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isFirstPerson;
import static com.nukateam.ntgl.common.data.constants.Animations.*;
import static com.nukateam.ntgl.common.data.constants.Animations.MELEE_END;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;

public class HammerAnimator extends GunAnimator {
    public static final String MELEE_POWER_END = "melee_power_end";
    public static final String MELEE_POWER = "melee_power";
    private boolean isPowered = false;

    public HammerAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getMeleeDelayAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType)) {
            if(HammerItem.isPowered(getStack())){
                isPowered = true;
                    var animation = playGunAnim(MELEE_POWER, HOLD_ON_LAST_FRAME);
                    animationHelper.syncAnimation(event, MELEE_POWER, meleeDelay);
                    return animation;
            }
            else {
                isPowered = false;
            }
        }
        return super.getMeleeDelayAnimation(event);
    }

    @Override
    protected RawAnimation getMeleeCooldownAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType)) {
            if (isPowered) {
                if (!animationHelper.hasAnimation(MELEE_POWER_END))
                    return getHoldAnimation(event);

                var animation = playGunAnim(MELEE_POWER_END, LOOP);
                animationHelper.syncAnimation(event, MELEE_POWER_END, meleeCooldown);
                return animation;
            }
        }
        return super.getMeleeCooldownAnimation(event);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType))
            return super.getHoldAnimation(event);
        else return null;
    }
}
