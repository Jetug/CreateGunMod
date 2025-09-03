package com.nukateam.cgs.client.renderers;

import net.minecraftforge.common.util.Lazy;

public class Renderers {
    public static final Lazy<HammerRenderer> HAMMER_RENDERER = Lazy.of(HammerRenderer::new);
}
