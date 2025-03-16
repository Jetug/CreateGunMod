package com.nukateam.cgs.client;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static com.nukateam.example.common.util.constants.Animations.RELOAD;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class ShotgunAnimator extends GunAnimator {
    protected final AnimationController<GunAnimator> COCK_CONTROLLER;

    private int ammo;

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
    public void tick(TickEvent event) {
        super.tick(event);
        if (event.phase == TickEvent.Phase.START) {
            if (isGun()) {
                this.ammo = Gun.getAmmo(getStack());
            }
        }
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
