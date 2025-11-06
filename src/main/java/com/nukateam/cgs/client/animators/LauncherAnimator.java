package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import net.minecraft.world.item.ItemDisplayContext;

import static software.bernie.geckolib.core.animation.Animation.LoopType.*;
import static software.bernie.geckolib.core.animation.RawAnimation.begin;

public class LauncherAnimator extends WeaponAnimator {
    public static final String EMPTY = "empty";
    public static final String RELOAD_BALLISTA_MANUAL = "reload_ballista_manual";
    public static final String RELOAD_BALLISTA_AUTO = "reload_ballista_auto";
    public static final String RELOAD_AUTO = "reload_auto";
    public static final String SHOT_BALLISTA = "shot_ballista";
    public static final String HIDE_SIGHTS = "hide_sights";

    private int ammoCount;
    private boolean isBallista;
    private boolean isAutoLauncher;
    private boolean hasAir;
    private boolean hasScope;

    protected final AnimationController<WeaponAnimator> MISC_CONTROLLER;

    public LauncherAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
        super(transformType, renderer);
        MISC_CONTROLLER = createController("misc_controller", animateMisc());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(MISC_CONTROLLER);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        var magazineAttachment = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, getStack()).getItem();
        try {
            if (itemStack.getItem() instanceof WeaponItem) {
                var data = getGunData();
                this.ammoCount = WeaponStateHelper.getAmmoCount(data);
                this.isBallista = magazineAttachment == AttachmentItems.BALLISTAZOOKA.get();
                this.isAutoLauncher = magazineAttachment == AttachmentItems.AUTO_LAUNCHER.get();
                this.hasAir = GunUtils.hasAir(data);
                this.hasScope = WeaponStateHelper.hasAttachmentEquipped(getStack(), AttachmentType.SCOPE);
            }
        }
        catch (IllegalStateException e){
            Gunsmithing.LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<WeaponAnimator> event) {
        if(ammoCount == 0){
            return playGunAnim(EMPTY, LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<WeaponAnimator> event) {
        if(isBallista){
            var animation = begin().then(getGunAnim(SHOT_BALLISTA), LOOP);
            animationHelper.syncAnimation(event, rate, SHOT_BALLISTA);
            return animation;
        }
        return super.getShootingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<WeaponAnimator> event) {
        if(isBallista){
            if (hasAir) {
                var animation = begin().then(getGunAnim(RELOAD_BALLISTA_AUTO), PLAY_ONCE);
                animationHelper.syncAnimation(event, reloadTime, RELOAD_BALLISTA_AUTO);
                return animation;
            } else {
                var animation = begin().then(getGunAnim(RELOAD_BALLISTA_MANUAL), PLAY_ONCE);
                animationHelper.syncAnimation(event, reloadTime, RELOAD_BALLISTA_MANUAL);
                return animation;
            }
        }
        else if(isAutoLauncher){
            var animation = begin().then(getGunAnim(RELOAD_AUTO), PLAY_ONCE);
            animationHelper.syncAnimation(event, reloadTime, RELOAD_AUTO);
            return animation;
        }
        return super.getDefaultReloadAnimation(event);
    }

    private AnimationController.AnimationStateHandler<WeaponAnimator> animateMisc() {
        return (event) -> {
            if(hasScope){
                var hideSights = begin().then(getGunAnim(HIDE_SIGHTS), LOOP);
                return event.setAndContinue(hideSights);
            }
            return PlayState.STOP;
        };
    }
}
