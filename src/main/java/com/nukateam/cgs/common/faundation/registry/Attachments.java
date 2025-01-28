package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.base.GunModifiers;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import net.minecraft.resources.ResourceLocation;

public class Attachments {
    public static final ResourceLocation SCOPE_LOCATION = new ResourceLocation(Ntgl.MOD_ID, "textures/hud/overlay/scope_long_overlay.png");

    public static final Scope SHORT_SCOPE = Scope.builder().aimFovModifier(0.7F).reticleOffset(1.55F).viewFinderDistance(1.1).modifiers(GunModifiers.SLOW_ADS).build();
    public static final Scope LONG_SCOPE = Scope.builder().aimFovModifier(0.25F).reticleOffset(1.4F)
            .viewFinderDistance(1.4).modifiers(GunModifiers.SLOWER_ADS).overlay(SCOPE_LOCATION).build();
}