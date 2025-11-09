package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.item.guns.HammerItem;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.client.util.handler.ClientMeleeHandler;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.config.weapon.WeaponConfig;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.holders.WeaponMode;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.nukateam.ntgl.client.util.helpers.TransformUtils.isFirstPerson;
import static software.bernie.geckolib.core.animation.Animation.LoopType.*;
import static software.bernie.geckolib.core.animation.RawAnimation.begin;

public class HammerAnimator extends WeaponAnimator {
    public static final String MELEE_POWER_END = "melee_power_end";
    public static final String MELEE_POWER_END2 = "melee_power_end2";
    public static final String MELEE_POWER = "melee_power";
    public static final String MELEE = "melee";
    public static final String MELEE_END = "melee_end";
    public static final String RELOAD_SHOT = "reload_shot";
    public static final String RELOAD_SHOT_EMPTY = "reload_shot_empty";
    private boolean isPowered = false;
    private int ammoCount;
    private boolean isShotPowered;
    private WeaponData data;
    private boolean isSecondary;

    public HammerAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        var meleeTracker = ClientMeleeHandler.getTracker(getEntity(), getArm());
        if(meleeTracker != null){
            var data = meleeTracker.getData();
            this.isSecondary = data.weaponMode == WeaponMode.SECONDARY;
        }
        if(itemStack != null &&!itemStack.isEmpty()) {
            this.data = getGunData();
        }
        this.ammoCount = WeaponStateHelper.getAmmoCount(data);
        this.isShotPowered = isShotPowered();
    }

    @Override
    protected RawAnimation getMeleeDelayAnimation(AnimationState<WeaponAnimator> event) {
        if(isFirstPerson(transformType)) {
            if(isSecondary && HammerItem.isPowered(data)){
                isPowered = true;
                try {
                    var animation = playGunAnim(MELEE_POWER, HOLD_ON_LAST_FRAME);
                    animationHelper.syncAnimation(event, meleeDelay, MELEE_POWER);
                    return animation;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                isPowered = false;
                var animation = playGunAnim(MELEE, HOLD_ON_LAST_FRAME);
                animationHelper.syncAnimation(event, meleeDelay, MELEE);
                return animation;
            }
        }
        return super.getMeleeDelayAnimation(event);
    }

    @Override
    protected RawAnimation getMeleeCooldownAnimation(AnimationState<WeaponAnimator> event) {
        if(isFirstPerson(transformType)) {
            if (isSecondary && isPowered) {
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
            else {
                var animation = playGunAnim(MELEE_END, HOLD_ON_LAST_FRAME);
                animationHelper.syncAnimation(event, meleeCooldown, MELEE_END);
                return animation;
            }
        }
        return super.getMeleeCooldownAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<WeaponAnimator> event) {
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
        return getStack().getItem() instanceof WeaponItem && WeaponStateHelper.hasAttachmentEquipped(getStack(), AttachmentType.MAGAZINE);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<WeaponAnimator> event) {
        if(isFirstPerson(transformType))
            return super.getHoldAnimation(event);
        else return null;
    }
}
