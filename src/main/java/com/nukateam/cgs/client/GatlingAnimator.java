package com.nukateam.cgs.client;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.util.handler.ShootingHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.cgs.client.handlers.ClientTickHandler.addTicker;
import static com.nukateam.ntgl.client.util.util.TransformUtils.isRightHand;
import static com.nukateam.ntgl.common.util.helpers.PlayerHelper.convertHand;

public class GatlingAnimator extends GunAnimator {
    private final Cycler cycler = new Cycler(1, getBarrelAmount());
    private boolean doCycle = true;
    public float prog0;
    public float prog;
    private float barrelRot;
    private int barrelId;

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
        addTicker(this, this::tick);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "barrelController", 0, this.animateBarrels()));
        super.registerControllers(controllerRegistrar);
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateBarrels() {
        return (event) -> {
            event.getController().setAnimationSpeed(1.0);

            if (TransformUtils.isHandTransform(this.transformType)) {
                LivingEntity entity = this.getEntity();
                var arm = TransformUtils.isRightHand(this.transformType) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;

                var isShooting = ShootingHandler.get().isShooting(entity, arm);
                var rate = GunModifierHelper.getRate(this.getStack());
                var barrelAnim = "barrel" + this.cycler.getCurrent();

                RawAnimation animation = null;
                if (isShooting && this.animationHelper.hasAnimation(barrelAnim)) {
                    Gunsmithing.LOGGER.info(barrelAnim);
                    animation = RawAnimation.begin().then(barrelAnim, Animation.LoopType.HOLD_ON_LAST_FRAME);
                    this.animationHelper.syncAnimation(event, barrelAnim, rate);
                }

                return event.setAndContinue(animation);
            }
            return PlayState.STOP;
        };
    }

    protected int getBarrelAmount(){
        return 4;
    }

    public void tick(){
        if (!(getStack().getItem() instanceof GunItem))
            return;
        float maxRot = 360f / getBarrelAmount();
        var entity = getEntity();
        var arm = isRightHand(transformType) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        var shootingHandler = ShootingHandler.get();
        var cooldown = ShootingHandler.get().getCooldown(entity, arm);
        var rate = GunModifierHelper.getRate(getStack());

        var cooldownPercent = shootingHandler.getCooldownPercent(entity, convertHand(arm));

        if(cooldown == rate){
            cycler.cycle();
            doCycle = false;
        }
        else if (cooldown == rate)
            doCycle = true;

        prog0 = prog;
        prog = /*(maxRot * cycler.getCurrent()) +*/ (maxRot * cooldownPercent);

//        Gunsmithing.LOGGER.info(String.valueOf(prog));
    }
}
