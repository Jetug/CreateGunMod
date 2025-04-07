package com.nukateam.cgs.client;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import mod.azure.azurelib.core.animation.*;
import net.minecraft.world.item.ItemDisplayContext;

import static mod.azure.azurelib.core.animation.Animation.LoopType.*;

public class FlintlockAnimator extends GunAnimator {
    public static final String EMPTY = "empty";
    private boolean hasAmmo;

    public FlintlockAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer<GunAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<GunAnimator> event) {
        if(!hasAmmo){
            return playGunAnim(EMPTY, LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        this.hasAmmo = Gun.hasAmmo(getStack());
    }
}
