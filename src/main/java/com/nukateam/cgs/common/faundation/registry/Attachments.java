package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.base.GunModifiers;
import com.nukateam.ntgl.common.base.holders.GripType;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

public class Attachments {
    public static final ResourceLocation SCOPE_LOCATION = new ResourceLocation(Ntgl.MOD_ID, "textures/hud/overlay/scope_long_overlay.png");

    public static final Scope SHORT_SCOPE = Scope.builder().aimFovModifier(0.85F).modifiers(GunModifiers.SLOW_ADS).build();
    public static final Scope LONG_SCOPE = Scope.builder().aimFovModifier(0.25F).reticleOffset(1.4F)
            .viewFinderDistance(1.4).modifiers(GunModifiers.SLOWER_ADS).overlay(SCOPE_LOCATION).build();

    public static final IGunModifier STEAM_ENGINE_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyFireRate(int rate) {
            return 1;
        }

        @Override
        public float modifyProjectileSpread(float spread) {
            return spread + 4f;
        }

        @Override
        public GripType modifyGripType(GripType gripType) {
            return IGunModifier.super.modifyGripType(gripType);
        }
    };

}