package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.example.common.util.constants.Animations.*;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends EngineAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String RELOAD_DRUM = "reload_drum";
    protected Cycler handleCycler = new Cycler(1, this.getBarrelAmount());

    private boolean hasDrum;

    protected final AnimationController<GunAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());
    protected final AnimationController<GunAnimator> GATLING_TRIGGER = createController( "gatling_trigger", event -> PlayState.CONTINUE)
            .triggerableAnim(HANDLE, begin().then(HANDLE, PLAY_ONCE))
            .triggerableAnim(VOID, begin().then(VOID, PLAY_ONCE));

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
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

        float cooldown = this.shootingHandler.getCooldown(getEntity(), this.arm);

        if (cooldown == (float)this.rate) {
            this.handleCycler.cycle();
        }
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
        HANDLE_CONTROLLER.setAnimation(begin().then(VOID, PLAY_ONCE));
        return super.getReloadingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<GunAnimator> event) {
        if(hasDrum) {
            var data = new GunData(getStack(), getEntity());
            var time = GunModifierHelper.getReloadTime(data);
            animationHelper.syncAnimation(event, RELOAD_DRUM, time);
            return begin().then(RELOAD_DRUM, LOOP);
        }
        return super.getDefaultReloadAnimation(event);
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateHandle() {
        return (event) -> {
            var gun = ((WeaponItem)getStack().getItem()).getGun();
            var animation = begin();
            var hasEngine = Gun.hasAttachmentEquipped(getStack(), gun, CgsAttachmentTypes.ENGINE);
            var hasFuel = FuelUtils.hasFuel(getGunData());

            if ((hasEngine && hasFuel) || hasAnimationPlaying(MAIN_CONTROLLER, RELOAD)) {
                return PlayState.STOP;
            } else {
                var reloadHandler = ClientReloadHandler.get();

                if (reloadHandler.isReloading(getEntity(), getArm()))
                    animation = begin().then(VOID, PLAY_ONCE);
                else {
                    var handleAnim = getCycledAnimation(event, HANDLE, this.handleCycler);
                    return handleAnim;
                }
            }

            return event.setAndContinue(animation);
        };
    }

    private boolean hasAnimationPlaying(AnimationController<GunAnimator> controller, String name) {
        var animation = controller.getCurrentAnimation();
        return animation != null && animation.animation().name().equals(name);
    }
}
