package com.nukateam.cgs.client;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
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
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends GunAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String ENGINE = "engine";

    protected final AnimationController<GunAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());
    protected final AnimationController<GunAnimator> ENGINE_CONTROLLER = createController("engine_controller", animateEngine());
    protected final AnimationController<GunAnimator> GATLING_TRIGGER = createController( "gatling_trigger", event -> PlayState.CONTINUE)
            .triggerableAnim(HANDLE, begin().then(HANDLE, PLAY_ONCE))
            .triggerableAnim(VOID, begin().then(VOID, PLAY_ONCE));

    protected int aTicks = 0;
    protected int ticks = 0;
    private int rate;

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getReloadingAnimation(AnimationState<GunAnimator> event) {
        HANDLE_CONTROLLER.setAnimation(begin().then(VOID, PLAY_ONCE));
        return super.getReloadingAnimation(event);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(HANDLE_CONTROLLER);
        controllerRegistrar.add(ENGINE_CONTROLLER);
        controllerRegistrar.add(GATLING_TRIGGER);
    }

    @Override
    protected int getBarrelAmount(){
        return 4;
    }

    @Override
    public void tick(TickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            super.tick(event);
            this.ticks++;
            this.rate = GunModifierHelper.getRate(getStack());

            playEngineSound();

            aTicks = Math.max(aTicks - 1, 0);
        }
    }

    private void playEngineSound() {
        if (shouldPlayEngineSound()) {
            var player = minecraft.player;
            var isShooting = shootingHandler.isShooting();
            var pitch = 1.18f - minecraft.level.random.nextFloat() * .25f;
            var volume = isShooting ? 6f : 0.6f;

            assert player != null;
            minecraft.level.playLocalSound(
                    player.position().x,
                    player.position().y,
                    player.position().z,
                    SoundEvents.CANDLE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    volume, pitch, false);

            AllSoundEvents.STEAM.playAt(minecraft.level, player.position(), volume / 16, .8f, false);
        }
    }

    protected boolean shouldPlayEngineSound() {
        var player = minecraft.player;
        var isShooting = shootingHandler.isShooting();
        var isNotPaused = !minecraft.getInstance().isPaused();
        var frequency = isShooting ? 5 : 20;

        return player != null && hasEngine()
                && this.ticks % frequency == 0
                && TransformUtils.isHandTransform(transformType)
                && hasGunInHands(player)
                && isNotPaused;
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

    protected AnimationController.AnimationStateHandler<GunAnimator> animateEngine() {
        return (event) -> {
            event.getController().setAnimationSpeed(1);
            var animation = begin().then(ENGINE, LOOP);

            if(shootingHandler.isShooting()) {
                animationHelper.syncAnimation(event, ENGINE, rate * getBarrelAmount());
            }

            return event.setAndContinue(animation);

        };
    }

    private boolean hasGunInHands(LocalPlayer player) {
        return player.getMainHandItem() == getStack() || player.getOffhandItem() == getStack();
    }

    private boolean hasEngine() {
        return !getStack().isEmpty()
                && Gun.hasAttachmentEquipped(getStack(), getGunItem().getGun(), CgsAttachmentTypes.ENGINE);
    }
}
