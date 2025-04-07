package com.nukateam.cgs.client;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.AllSoundEvents;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.event.TickEvent;

import static com.nukateam.example.common.util.constants.Animations.RELOAD;
import static com.nukateam.ntgl.common.util.util.GunModifierHelper.isGun;
import static mod.azure.azurelib.core.animation.Animation.LoopType.LOOP;
import static mod.azure.azurelib.core.animation.Animation.LoopType.PLAY_ONCE;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public abstract class EngineAnimator extends GunAnimator {
    public static final String HANDLE = "handle";
    public static final String VOID = "void";
    public static final String ENGINE = "engine";

    protected final AnimationController<GunAnimator> ENGINE_CONTROLLER
            = createController("engine_controller", animateEngine());

    protected int aTicks = 0;
    protected int ticks = 0;
    private int rate;

    public EngineAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(ENGINE_CONTROLLER);
    }

    @Override
    public void tick(TickEvent event) {
        if (event.phase == TickEvent.Phase.START && isGun(getStack())) {
            super.tick(event);
            this.ticks++;
            var data = new GunData(getStack(), getEntity());
            this.rate = GunModifierHelper.getRate(data);

            playEngineSound();

            aTicks = Math.max(aTicks - 1, 0);
        }
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateEngine() {
        return (event) -> {
            if(TransformUtils.isHandTransform(transformType)) {
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
            var isShooting = shootingHandler.isShooting();
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
        var player = minecraft.player;
        var isShooting = shootingHandler.isShooting(getEntity(), arm);
        var isNotPaused = !minecraft.getInstance().isPaused();
        var frequency = isShooting ? 10 : 60;

        var mainGunData = new GunData(getEntity().getMainHandItem(), getEntity());
        var offGunData = new GunData(getEntity().getOffhandItem(), getEntity());

        var isVisible = arm == HumanoidArm.RIGHT || (arm == HumanoidArm.LEFT
                && GunModifierHelper.isOneHanded(mainGunData)
                && GunModifierHelper.isOneHanded(offGunData));

        return player != null
                && hasEngine()
                && isVisible
                && this.ticks % frequency == 0
                && TransformUtils.isHandTransform(transformType)
                && hasGunInHands(player)
                && isNotPaused;
    }

    protected boolean hasGunInHands(LocalPlayer player) {
        return player.getMainHandItem() == getStack() || player.getOffhandItem() == getStack();
    }

    protected boolean hasEngine(){
        return !getStack().isEmpty()
                && Gun.hasAttachmentEquipped(getStack(),
                getGunItem().getGun(),
                CgsAttachmentTypes.ENGINE);
    }
}
