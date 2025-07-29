package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.AllSoundEvents;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.common.util.util.GunModifierHelper.isGun;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public abstract class EngineAnimator extends GunAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String ENGINE = "engine";

    protected final AnimationController<GunAnimator> ENGINE_CONTROLLER
            = createController("engine_controller", animateEngine());

    protected int aTicks = 0;
    protected int ticks = 0;
    protected int rate;

    public EngineAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(ENGINE_CONTROLLER);
    }

    @Override
    protected void tickStart() {
        if (isGun(getStack())) {
            super.tickStart();
            this.ticks++;
            var data = new GunData(getStack(), getEntity());
            this.rate = GunModifierHelper.getRate(data);

            playEngineSound();

            aTicks = Math.max(aTicks - 1, 0);
        }
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateEngine() {
        return (event) -> {
            if(isEngineWorking()) {
                event.getController().setAnimationSpeed(1);
                var animation = begin().then(ENGINE, LOOP);

                if (shootingHandler.isOnCooldown(getEntity(), getArm())){
                    animationHelper.syncAnimation(event, ENGINE, rate * getBarrelAmount());
                }

                return event.setAndContinue(animation);
            }
            return PlayState.STOP;
        };
    }

    private void playEngineSound() {
        if (shouldPlayEngineSound()) {
            var player = minecraft.player;
            var isShooting = shootingHandler.isShooting(getEntity(), getArm());
            var pitch = 1.18f - minecraft.level.random.nextFloat() * .25f;
            var volume = isShooting ? 6f : 0.5f;

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

    private boolean shouldPlayEngineSound() {
        var isShooting = shootingHandler.isShooting(getEntity(), arm);
        var frequency = isShooting ? 5 : 20;
        var tick = this.ticks % frequency == 0;

        return isEngineWorking() && tick;
    }

    private boolean isEngineWorking() {
        var player = minecraft.player;
        if(player == null) return false;
        var mainGunData = new GunData(getEntity().getMainHandItem(), getEntity());
        var offGunData = new GunData(getEntity().getOffhandItem(), getEntity());
        var i1 = GunModifierHelper.isOneHanded(mainGunData);
        var i2 = GunModifierHelper.isOneHanded(offGunData);
        var arm = this.getArm();
        var isNotPaused = !minecraft.getInstance().isPaused();
        var isVisible = arm == InteractionHand.MAIN_HAND || (arm == InteractionHand.OFF_HAND && i1 && i2);
        var hasEngine = hasEngine();
        var isHandTransform = TransformUtils.isHandTransform(transformType);
        var hasGunInHands = hasGunInHands(player);
        var hasFuel = FuelUtils.hasFuel(new GunData(getStack(),getEntity()));

        return hasEngine
                && hasFuel
                && isVisible
                && isHandTransform
                && hasGunInHands
                && isNotPaused;
    }

    protected boolean hasGunInHands(LocalPlayer player) {
        return player.getMainHandItem().getItem() == getStack().getItem() || player.getOffhandItem().getItem() == getStack().getItem();
    }

    protected boolean hasEngine(){
        return !getStack().isEmpty() && Gun.hasAttachmentEquipped(getStack(), CgsAttachmentTypes.ENGINE);
    }
}
