package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.item.guns.HammerItem;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isFirstPerson;
import static com.nukateam.ntgl.common.data.constants.Animations.*;
import static com.nukateam.ntgl.common.data.constants.Animations.MELEE_END;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class HammerAnimator extends GunAnimator {
    public static final String MELEE_POWER_END = "melee_power_end";
    public static final String MELEE_POWER_END2 = "melee_power_end2";
    public static final String MELEE_POWER = "melee_power";
    public static final String RELOAD_SHOT = "reload_shot";
    public static final String RELOAD_SHOT_EMPTY = "reload_shot_empty";
    private boolean isPowered = false;
    private int ammoCount;
    private boolean isShotPowered;
    private GunData data;

    public HammerAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        if(itemStack != null &&!itemStack.isEmpty()) {
            this.data = getGunData();
        }
        this.ammoCount = GunStateHelper.getAmmoCount(data);
        this.isShotPowered = isShotPowered();
    }

    @Override
    protected RawAnimation getMeleeDelayAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType)) {
            if(HammerItem.isPowered(data)){
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

                var animations = new ArrayList<>(List.of(MELEE_POWER_END));
                var animation = begin().then(getGunAnim(MELEE_POWER_END), PLAY_ONCE);

                if(isShotPowered){
                    animation.then(getGunAnim(MELEE_POWER_END2), PLAY_ONCE);
                    animations.add(MELEE_POWER_END2);
                }

                animationHelper.syncAnimation(event, meleeCooldown, animations);
                return animation;
            }
        }
        return super.getMeleeCooldownAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<GunAnimator> event) {
        if(isShotPowered()){
            var animations = new ArrayList<>(List.of(RELOAD_SHOT));
            var animation = begin().then(getGunAnim(RELOAD_SHOT), PLAY_ONCE);

            if(ammoCount == 0){
                animation.then(getGunAnim(RELOAD_SHOT_EMPTY), PLAY_ONCE);
                animations.add(RELOAD_SHOT_EMPTY);
            }

            animationHelper.syncAnimation(event, reloadTime, animations);
            return animation;
        }
        return super.getDefaultReloadAnimation(event);
    }

    private boolean isShotPowered() {
        return getStack().getItem() instanceof WeaponItem && GunStateHelper.hasAttachmentEquipped(getStack(), AttachmentType.MAGAZINE);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType))
            return super.getHoldAnimation(event);
        else return null;
    }
}
