package com.nukateam.cgs.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.render.BaseGunRenderer;
import com.nukateam.ntgl.common.base.DynamicGunModifier;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class BaseGunItem extends GunItem {
    private final Lazy<BaseGunRenderer> RENDERER = Lazy.of(() -> new BaseGunRenderer());

    public BaseGunItem(Properties properties) {
        super(properties);
    }

    public BaseGunItem(Supplier<DynamicGunModifier> modifierFactory, Properties properties) {
        super(modifierFactory, properties);
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return false;
    }
}