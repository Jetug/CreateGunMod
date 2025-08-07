package com.nukateam.cgs.client.animators;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import com.nukateam.ntgl.common.util.helpers.PlayerHelper;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import mod.azure.azurelib.core.animation.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import static mod.azure.azurelib.core.animation.Animation.LoopType.*;

public class FlintlockAnimator extends GunAnimator {
    public static final String EMPTY = "empty";
    public static final String ONE_HAND = "_one_hand";
    private boolean hasAmmo;

    public FlintlockAnimator(ItemDisplayContext transformType, DynamicGunRenderer<GunAnimator> renderer) {
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
        this.hasAmmo = GunStateHelper.hasAmmo(getStack());
    }

    @Override
    protected String getGunAnim(String name) {
        var entity = this.getEntity();
        var currentItem = entity.getItemInHand(this.arm);
        var isOneHanded = this.isOneHanded(currentItem);
        return (isOneHanded) && this.animationHelper.hasAnimation(name + ONE_HAND) ? name + ONE_HAND : name;
    }
}
