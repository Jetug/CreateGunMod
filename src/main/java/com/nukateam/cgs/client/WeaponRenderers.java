package com.nukateam.cgs.client;

import com.nukateam.cgs.client.renderers.weapon.BaseWeaponRenderer;
import com.nukateam.cgs.client.renderers.weapon.GatlingRenderer;
import com.nukateam.cgs.client.renderers.weapon.HammerRenderer;
import net.minecraftforge.common.util.Lazy;

public class WeaponRenderers {
    public static final Lazy<HammerRenderer> HAMMER_RENDERER = Lazy.of(HammerRenderer::new);
    public static final Lazy<GatlingRenderer> GATLING_RENDERER = Lazy.of(GatlingRenderer::new);
}
