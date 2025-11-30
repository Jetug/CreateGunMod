package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.data.config.weapon.WeaponConfig;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.util.helpers.PlayerHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import software.bernie.geckolib.core.animation.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import static com.nukateam.ntgl.common.data.constants.Animations.RELOAD;
import static com.nukateam.ntgl.common.data.constants.Animations.SHOT;
import static software.bernie.geckolib.core.animation.Animation.LoopType.*;

public class FlintlockAnimator extends WeaponAnimator {
    public static final String EMPTY = "empty";
    public static final String ONE_HAND = "_one_hand";
    public static final String SHOT_MULTI = "shot_multi";
    public static final String RELOAD_MULTI = "reload_multi";
    private boolean hasAmmo;
    private boolean hasMagazine;

    public FlintlockAnimator(ItemDisplayContext transformType, DynamicWeaponRenderer<WeaponAnimator> renderer) {
        super(transformType, renderer);
    }

    @Override
    protected RawAnimation getHoldAnimation(AnimationState<WeaponAnimator> event) {
        if(!hasAmmo){
            return playGunAnim(EMPTY, LOOP);
        }
        return super.getHoldAnimation(event);
    }

    @Override
    protected void tickStart() {
        super.tickStart();
        this.hasAmmo = WeaponStateHelper.hasAmmo(getStack());
        this.hasMagazine = hasRevolvingChambersEquiped();
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationState<WeaponAnimator> event) {
        if(hasMagazine){
            animationHelper.syncAnimation(event, rate, SHOT_MULTI);
            return playGunAnim(SHOT_MULTI, LOOP);
        }
        return super.getShootingAnimation(event);
    }

    @Override
    protected RawAnimation getDefaultReloadAnimation(AnimationState<WeaponAnimator> event) {
        if(hasMagazine){
            animationHelper.syncAnimation(event, reloadTime, RELOAD_MULTI);
            return playGunAnim(RELOAD_MULTI, LOOP);
        }
        return super.getDefaultReloadAnimation(event);
    }

    @Override
    protected String getGunAnim(String name) {
        var entity = this.getEntity();
        var currentItem = entity.getItemInHand(this.arm);
        var isOneHanded = this.isOneHanded(currentItem);
        return (isOneHanded) && this.animationHelper.hasAnimation(name + ONE_HAND) ? name + ONE_HAND : name;
    }

    private boolean hasRevolvingChambersEquiped() {
        return WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, getStack()).getItem() == CgsAttachments.REVOLVING_CHAMBERS.get();
    }
}
