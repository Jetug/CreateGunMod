package com.nukateam.cgs.client;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.ClientHandler;
import com.nukateam.ntgl.client.util.handler.ShootingData;
import com.nukateam.ntgl.client.util.handler.ShootingHandler;
import com.nukateam.ntgl.client.util.util.TransformUtils;
import com.nukateam.ntgl.common.util.util.Cycler;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isRightHand;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;
import static mod.azure.azurelib.core.animation.RawAnimation.begin;

public class GatlingAnimator extends GunAnimator {
    public static final String HANDLE = "handle";
    protected final AnimationController<GunAnimator> HANDLE_CONTROLLER = createController("handle_controller", animateHandle());

    public GatlingAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    protected AnimationController.AnimationStateHandler<GunAnimator> animateHandle() {
        return (event) -> getCycledAnimation(event, HANDLE, this.barrelCycler);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(HANDLE_CONTROLLER);
    }

    @Override
    protected int getBarrelAmount(){
        return 4;
    }

    @Override
    public void tick() {
        super.tick();
//        var fireTimer = GunModifierHelper.getFireDelay(getStack());
//        var data = ShootingHandler.get().getShootingData(TransformUtils.getHand(transformType));
//        if(fireTimer == data.fireTimer){
//            barrelCycler.cycle();
//        }
//        Gunsmithing.LOGGER.info("barrel id: " + barrelCycler.getCurrent());
    }
}
