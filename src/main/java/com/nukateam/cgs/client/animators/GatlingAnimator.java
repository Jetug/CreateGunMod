package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.example.common.util.constants.Animations.*;
import static software.bernie.geckolib.core.animation.Animation.LoopType.*;
import static software.bernie.geckolib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends EngineAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String RELOAD_DRUM = "reload_drum";
    protected Cycler handleCycler = new Cycler(1, this.getBarrelAmount());

    private boolean hasDrum;

    protected final AnimationController<WeaponAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());
    protected final AnimationController<WeaponAnimator> GATLING_TRIGGER = createController( "gatling_trigger", event -> PlayState.CONTINUE)
            .triggerableAnim(HANDLE, begin().then(HANDLE, PLAY_ONCE))
            .triggerableAnim(VOID, begin().then(VOID, PLAY_ONCE));

    public GatlingAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
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
        var magazine = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, getStack());
        this.hasDrum = magazine.is(CgsAttachments.GATLING_DRUM.get());

        float cooldown = this.shootingHandler.getCooldown(getEntity(), this.arm);

        if (cooldown == (float)this.rate) {
            this.handleCycler.cycle();
        }
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<WeaponAnimator> event) {
        HANDLE_CONTROLLER.setAnimation(begin().then(VOID, PLAY_ONCE));
        return super.getReloadingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<WeaponAnimator> event) {
        if(hasDrum) {
            var data = new WeaponData(getStack(), getEntity());
            var time = WeaponModifierHelper.getReloadTime(data);
            animationHelper.syncAnimation(event, RELOAD_DRUM, time);
            return begin().then(RELOAD_DRUM, LOOP);
        }
        return super.getDefaultReloadAnimation(event);
    }

    protected AnimationController.AnimationStateHandler<WeaponAnimator> animateHandle() {
        return (event) -> {
            var gun = ((WeaponItem)getStack().getItem()).getConfig();
            var animation = begin();
            var hasEngine = WeaponStateHelper.hasAttachmentEquipped(getStack(), CgsAttachmentTypes.ENGINE);
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

    private boolean hasAnimationPlaying(AnimationController<WeaponAnimator> controller, String name) {
        var animation = controller.getCurrentAnimation();
        return animation != null && animation.animation().name().equals(name);
    }
}
