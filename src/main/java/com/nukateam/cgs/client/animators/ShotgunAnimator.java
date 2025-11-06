package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.example.common.util.constants.Animations;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.weapon.WeaponConfig;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.data.WeaponData;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationController.AnimationStateHandler;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.nukateam.ntgl.common.util.util.WeaponModifierHelper.*;
import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;
import static software.bernie.geckolib.core.animation.Animation.LoopType.PLAY_ONCE;
import static software.bernie.geckolib.core.animation.RawAnimation.begin;

public class ShotgunAnimator extends WeaponAnimator {
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
    protected final AnimationController<WeaponAnimator> COCK_CONTROLLER;
    protected final AnimationController<WeaponAnimator> FLASH_CONTROLLER;

    private int ammo;
    private boolean hasDrums;
    private boolean hasPumps;
    private Cycler cockCycler = new Cycler(1, 2);
    private boolean isAmmoEven;
    private int cockInfo;

    public ShotgunAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
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

        var magazine = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, getStack());
        var data = getGunData();
        this.ammo = WeaponStateHelper.getAmmoCount(data);
        this.hasDrums = magazine.is(AttachmentItems.SHOTGUN_DRUM.get());
        this.hasPumps = magazine.is(AttachmentItems.SHOTGUN_PUMP.get());
        this.isAmmoEven =  GunUtils.isAmmoEven(data);
        this.cockInfo =  GunUtils.getCock(getStack());

        var cooldown = this.shootingHandler.getCooldown(getEntity(), this.arm);

        if (cockCycler == null)
            cockCycler = new Cycler(1, 2);

        if (cooldown == rate) {
            cockCycler.cycle();
        }
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<WeaponAnimator> event) {
        var animation = begin();
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
    protected RawAnimation getStartReloadAnimation(AnimationState<WeaponAnimator> event) {
        if(hasPumps) {
            var data = getGunData();
            int time = getReloadStart(data);
            this.animationHelper.syncAnimation(event, RELOAD_PUMP_START, time);
            return RawAnimation.begin().then(RELOAD_PUMP_START, PLAY_ONCE);
        }
        else return super.getStartReloadAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<WeaponAnimator> event) {
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
    protected RawAnimation getEndReloadAnimation(AnimationState<WeaponAnimator> event) {
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

    protected @NotNull WeaponData getGunData() {
        return new WeaponData(getStack(), getEntity());
    }

    private AnimationStateHandler<WeaponAnimator> animateCock() {
        return (event) -> {
            var name = "";

            switch (cockInfo){
                case  0 ->  name = EMPTY_BOTH;
                case  1 ->  name = EMPTY_LEFT;
                case  2 ->  name = FULL;
            }

//            if (ammo == 0){
//                name = EMPTY_BOTH;
//            }
//            else if(isAmmoEven){
//                name = FULL;
//            }
//            else {
//                name = EMPTY_LEFT;
//            }

            var animation = begin().then(name, LOOP);
            animationHelper.syncAnimation(event, name, SHOT_TIME);
            return event.setAndContinue(animation);
        };
    }

    private AnimationStateHandler<WeaponAnimator> animateFlash() {
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
