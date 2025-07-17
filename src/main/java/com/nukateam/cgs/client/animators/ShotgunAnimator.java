package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.example.common.util.constants.Animations;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.util.util.GunData;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.animation.AnimationController.AnimationStateHandler;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.nukateam.ntgl.common.util.util.GunModifierHelper.*;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class ShotgunAnimator extends GunAnimator {
    public static final String SHOT_DRUM = "shot_drum";
    public static final String SHOT_PUMP = "shot_pump";
    public static final String RELOAD_DRUM = "reload_drum";
    public static final String RELOAD_PUMP_LEFT = "reload_pump_left";
    public static final String RELOAD_PUMP_RIGHT = "reload_pump_right";
    public static final String RELOAD_PUMP_START = "reload_pump_start";
    public static final String RELOAD_PUMP_END = "reload_pump_end";
    public static final String EMPTY_BOTH = "empty_both";
    public static final String FULL = "full";
    public static final String EMPTY_LEFT = "empty_left";
    public static final String SHOT_LEFT = "shot_left";
    public static final String SHOT_RIGHT = "shot_right";
    public static final String WAIT = "wait";

    public static final int SHOT_TIME = 2;
    protected final AnimationController<GunAnimator> COCK_CONTROLLER;
    protected final AnimationController<GunAnimator> FLASH_CONTROLLER;

    private int ammo;
    private boolean hasDrums;
    private boolean hasPumps;
    private Cycler cockCycler = new Cycler(1, 2);

    public ShotgunAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
        COCK_CONTROLLER = createController("cock_controller", animateCock());
        FLASH_CONTROLLER = createController("flash_controller", animateFlash());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(COCK_CONTROLLER);
        controllerRegistrar.add(FLASH_CONTROLLER);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        if (!isGun()) return;

        var magazine = Gun.getAttachmentItem(AttachmentType.MAGAZINE, getStack());

        this.ammo = Gun.getAmmo(getStack());
        this.hasDrums = magazine.is(AttachmentItems.SHOTGUN_DRUM.get());
        this.hasPumps = magazine.is(AttachmentItems.SHOTGUN_PUMP.get());

        var cooldown = this.shootingHandler.getCooldown(getEntity(), this.arm);

        if (cockCycler == null)
            cockCycler = new Cycler(1, 2);

        if (cooldown == rate) {
            cockCycler.cycle();
        }
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<GunAnimator> event) {
        var animation = begin();
        var isAmmoEven = GunUtils.isAmmoEven(getStack());
        var animations = new ArrayList<String>();

        var shotAnim = this.getGunAnim(Animations.SHOT);
        animation.then(shotAnim, PLAY_ONCE);
        animations.add(shotAnim);

        if((hasDrums || hasPumps) && isAmmoEven && ammo > 0) {
            var reloadAnim = hasDrums ?
                    this.getGunAnim(SHOT_DRUM) :
                    this.getGunAnim(SHOT_PUMP);
            animation.then(reloadAnim, PLAY_ONCE);
            animations.add(reloadAnim);
        }

        if(isAmmoEven) {
            rate = 20;
        }
        animation.then(this.getGunAnim(Animations.HOLD), LOOP);
        this.animationHelper.syncAnimation(event, rate, animations);
        return animation;
    }

    @Override
    protected RawAnimation getStartReloadAnimation(AnimationState<GunAnimator> event) {
        if(hasPumps) {
            var data = getGunData();
            int time = getReloadStart(data);
            this.animationHelper.syncAnimation(event, RELOAD_PUMP_START, time);
            return RawAnimation.begin().then(RELOAD_PUMP_START, PLAY_ONCE);
        }
        else return super.getStartReloadAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<GunAnimator> event) {
        var data = getGunData();
        var reloadTime = getReloadTime(data);
        if(hasDrums){
            this.animationHelper.syncAnimation(event, reloadTime, RELOAD_DRUM, SHOT_DRUM);
            return begin().then(RELOAD_DRUM, PLAY_ONCE).then(SHOT_DRUM, LOOP);
        }
        else if(hasPumps){
            var lessThenHalf = ammo <= getMaxAmmo(data) / 2;
            var name = lessThenHalf ? RELOAD_PUMP_LEFT: RELOAD_PUMP_RIGHT;

            this.animationHelper.syncAnimation(event, name, reloadTime);
            return begin().then(name, LOOP);

        }
        else return super.getDefaultReloadAnimation(event);
    }

    @Override
    protected RawAnimation getEndReloadAnimation(AnimationState<GunAnimator> event) {
        if(hasPumps) {
            var data = getGunData();
            int time = getReloadEnd(data);
            this.animationHelper.syncAnimation(event, time, RELOAD_PUMP_END, SHOT_PUMP);
            return RawAnimation.begin()
                    .then(RELOAD_PUMP_END, PLAY_ONCE)
                    .then(SHOT_PUMP, PLAY_ONCE)
                    .then(Animations.HOLD, LOOP);
        }
        else return super.getEndReloadAnimation(event);
    }

    protected @NotNull GunData getGunData() {
        return new GunData(getStack(), getEntity());
    }

    private AnimationStateHandler<GunAnimator> animateCock() {
        return (event) -> {
            var ammo = Gun.getAmmo(getStack());
            var name = "";

            if (ammo == 0){
                name = EMPTY_BOTH;
            }
            else if(GunUtils.isAmmoEven(getStack())){
                name = FULL;
            }
            else {
                name = EMPTY_LEFT;
            }

            var animation = begin().then(name, LOOP);
            animationHelper.syncAnimation(event, name, SHOT_TIME);
            return event.setAndContinue(animation);
        };
    }

    private AnimationStateHandler<GunAnimator> animateFlash() {
        return (event) -> {
            var cooldown = shootingHandler.getCooldown(getEntity(), arm);
                var isShooting = rate <= cooldown;
            var sas = cooldown > 0;

            if(shootingHandler.isShooting(getEntity(), arm)) {
//                var name = cockCycler.getCurrent() == 1 ? SHOT_LEFT : SHOT_RIGHT;
//                var animation = begin().then(name, PLAY_ONCE).then(WAIT, LOOP);
//                animationHelper.syncAnimation(event, name, SHOT_TIME);
//                return event.setAndContinue(animation);

                return event.setAndContinue(begin().then(SHOT_LEFT, PLAY_ONCE).then(WAIT, LOOP));
            }
            return event.setAndContinue(begin().then(WAIT, LOOP));
        };
    }

    private boolean isGun() {
        return getStack().getItem() instanceof WeaponItem;
    }
}
