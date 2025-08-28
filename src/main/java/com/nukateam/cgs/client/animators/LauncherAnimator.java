package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;

import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class LauncherAnimator extends GunAnimator {
    public static final String EMPTY = "empty";
    public static final String RELOAD_BALLISTA_MANUAL = "reload_ballista_manual";
    public static final String RELOAD_BALLISTA_AUTO = "reload_ballista_auto";
    public static final String RELOAD_AUTO = "reload_auto";
    public static final String SHOT_BALLISTA = "shot_ballista";

    private int ammoCount;
    private boolean isBallista;
    private boolean isAutoLauncher;
    private boolean hasAir;

    public LauncherAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }
    @Override
    protected void tickStart() {
        super.tickStart();
        var magazineAttachment = GunStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, getStack()).getItem();
        try {
            if (itemStack.getItem() instanceof WeaponItem) {
                var data = getGunData();
                this.ammoCount = GunStateHelper.getAmmoCount(data);
                this.isBallista = magazineAttachment == AttachmentItems.BALLISTAZOOKA.get();
                this.isAutoLauncher = magazineAttachment == AttachmentItems.AUTO_LAUNCHER.get();
                this.hasAir = GunUtils.hasAir(data);
            }
        }
        catch (IllegalStateException e){
            Gunsmithing.LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(ammoCount == 0){
            return playGunAnim(EMPTY, LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<GunAnimator> event) {
        if(isBallista){
            var animation = begin().then(getGunAnim(SHOT_BALLISTA), LOOP);
            animationHelper.syncAnimation(event, rate, SHOT_BALLISTA);
            return animation;
        }
        return super.getShootingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<GunAnimator> event) {
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
}
