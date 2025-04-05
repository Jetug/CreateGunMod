package com.nukateam.cgs.client;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.AllSoundEvents;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static com.nukateam.example.common.util.constants.Animations.*;
import static com.nukateam.ntgl.common.util.util.GunModifierHelper.isGun;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends EngineAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String RELOAD_DRUM = "reload_drum";

    private boolean hasDrum;

    protected final AnimationController<GunAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());
    protected final AnimationController<GunAnimator> GATLING_TRIGGER = createController( "gatling_trigger", event -> PlayState.CONTINUE)
            .triggerableAnim(HANDLE, begin().then(HANDLE, PLAY_ONCE))
            .triggerableAnim(VOID, begin().then(VOID, PLAY_ONCE));

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(HANDLE_CONTROLLER);
        controllerRegistrar.add(GATLING_TRIGGER);
    }

    @Override
    protected int getBarrelAmount(){
        return 4;
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        var magazine = Gun.getAttachmentItem(AttachmentType.MAGAZINE, getStack());
        this.hasDrum = magazine.is(AttachmentItems.GATLING_DRUM.get());
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
        HANDLE_CONTROLLER.setAnimation(begin().then(VOID, PLAY_ONCE));
        return super.getReloadingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAminmation(AnimationState<GunAnimator> event) {
        if(hasDrum) {
            var time = GunModifierHelper.getReloadTime(getStack());
            animationHelper.syncAnimation(event, RELOAD_DRUM, time);
            return begin().then(RELOAD_DRUM, LOOP);
        }
        return super.getDefaultReloadAminmation(event);
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateHandle() {
        return (event) -> {
            var gun = ((GunItem)getStack().getItem()).getGun();
            var animation = begin();

            if (Gun.hasAttachmentEquipped(getStack(), gun, CgsAttachmentTypes.ENGINE)
                    || MAIN_CONTROLLER.getCurrentAnimation().animation().name().equals(RELOAD)) {
                return PlayState.STOP;
            } else {
                var reloadHandler = ClientReloadHandler.get();

                if (reloadHandler.isReloading(getEntity(), getArm()))
                    animation = begin().then(VOID, PLAY_ONCE);
                else return getCycledAnimation(event, HANDLE, this.barrelCycler);
            }

            return event.setAndContinue(animation);
        };
    }
}
