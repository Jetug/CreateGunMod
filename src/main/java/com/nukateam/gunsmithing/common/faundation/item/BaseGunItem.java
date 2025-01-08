package com.nukateam.gunsmithing.common.faundation.item;

import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.gunsmithing.client.render.BaseGunRenderer;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.ItemStack;

public class BaseGunItem extends GunItem {
    private static final BaseGunRenderer RENDERER = new BaseGunRenderer();

    public BaseGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER;
    }
}