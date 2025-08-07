package com.nukateam.cgs.client.animators;

import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;

import static com.nukateam.ntgl.client.util.util.TransformUtils.isFirstPerson;
import static com.nukateam.ntgl.common.data.constants.Animations.HOLD;
import static mod.azure.azurelib.core.animation.Animation.LoopType.*;

public class HammerAnimator extends GunAnimator {
    public HammerAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(isFirstPerson(transformType))
            return super.getHoldAnimation(event);
        else return null;
    }
}
