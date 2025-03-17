package com.nukateam.cgs.client;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.example.common.util.constants.Animations;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import mod.azure.azurelib.core.animation.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static com.nukateam.cgs.common.ntgl.modifiers.ShotgunModifier.isAmmoEven;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class ShotgunAnimator extends GunAnimator {
    protected final AnimationController<GunAnimator> COCK_CONTROLLER;

    private int ammo;
    private boolean hasDrums;
    private boolean hasPumps;

    public ShotgunAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
        COCK_CONTROLLER = createController("cock_controller", animateCock());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(COCK_CONTROLLER);
    }


    @Override
    protected void tickStart() {
        super.tickStart();
        if (isGun()) {
            this.ammo = Gun.getAmmo(getStack());
            var magazine = Gun.getAttachmentItem(AttachmentType.MAGAZINE, getStack());

            this.hasDrums = magazine.is(AttachmentItems.SHOTGUN_DRUM.get());
            this.hasPumps = magazine.is(AttachmentItems.SHOTGUN_PUMP.get());
        }
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<GunAnimator> event) {
        var shot = this.getGunAnim(Animations.SHOT);
        var reload = this.getGunAnim("reload_drum");
        var animation = begin();
        var isAmmoEven = isAmmoEven(getStack());
        animation.then(shot, PLAY_ONCE).then(reload, LOOP);

//        if(hasDrums && isAmmoEven) {
//            animation.then(reload, LOOP);
//        }

        if(isAmmoEven) {
            rate = 20;
        }

        this.animationHelper.syncAnimations(event, rate, Animations.SHOT, "reload_drum");
        return animation;
    }

    private AnimationController.AnimationStateHandler<GunAnimator> animateCock() {
        return (event) -> {
            var ammo = Gun.getAmmo(getStack());
            var name = switch (ammo) {
                case 0 -> "empty_both";
                case 1 -> "empty_left";
                default -> "full";
            };

            var animation = begin().then(name, LOOP);
            animationHelper.syncAnimation(event, name, rate);
            return event.setAndContinue(animation);
        };
    }

    private boolean isGun() {
        return getStack().getItem() instanceof GunItem;
    }
}
