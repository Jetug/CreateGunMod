package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.client.animators.FlintlockAnimator;
import com.nukateam.cgs.client.renderers.FlintlockRenderer;
import com.nukateam.cgs.client.renderers.GatlingRenderer;
import com.nukateam.geo.render.*;
import com.nukateam.ntgl.client.animators.*;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;

import java.util.function.BiFunction;

public class FlintlockItem extends CgsGunItem {
    private final Lazy<FlintlockRenderer> RENDERER = Lazy.of(() -> new FlintlockRenderer());

    public FlintlockItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public BiFunction<ItemDisplayContext, DynamicWeaponRenderer<WeaponAnimator>, WeaponAnimator> getAnimatorFactory() {
        return FlintlockAnimator::new;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

}