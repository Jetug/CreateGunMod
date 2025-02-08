package com.nukateam.cgs.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.render.BaseGunRenderer;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

public class BaseGunItem extends GunItem {
    private final Lazy<BaseGunRenderer> RENDERER = Lazy.of(() -> new BaseGunRenderer());

    public BaseGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }
}