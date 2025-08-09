package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.HammerAnimator;
import com.nukateam.cgs.client.renderers.GatlingRenderer;
import com.nukateam.cgs.client.renderers.HammerRenderer;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class HammerItem extends CgsGunItem {
    private final Lazy<HammerRenderer> RENDERER = Lazy.of(HammerRenderer::new);

    public HammerItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(ItemStack stack){
        return GunStateHelper.getAmmoCount(stack) > 0;
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicGunRenderer<GunAnimator>, GunAnimator> getAnimatorFactory() {
        return HammerAnimator::new;
    }
}