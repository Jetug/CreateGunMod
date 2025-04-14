package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.render.BaseGunRenderer;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;

import java.util.Set;

public class CgsGunItem extends GunItem {
    private final Lazy<BaseGunRenderer> RENDERER = Lazy.of(() -> new BaseGunRenderer());
    private boolean needFuel = false;

    public CgsGunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    public boolean isNeedFuel() {
        return needFuel;
    }

    public CgsGunItem setNeedFuel(boolean needFuel) {
        this.needFuel = needFuel;
        return this;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(stack, level, entity, pSlotId, pIsSelected);

        if (entity instanceof LivingEntity livingEntity) {
            onEngineTick(new GunData(stack, livingEntity));
        }
    }

    private static void onEngineTick(GunData data) {
        var fuel = GunModifierHelper.getFuelTypes(data);

        if(!fuel.isEmpty() && isInHand(data)){
            if(fuel.contains(FuelType.BURNABLE)){
                FuelUtils.addFuel(data, FuelType.BURNABLE, -50);
            }
        }
    }

    private static boolean isInHand(GunData data) {
        var mainHand = data.shooter.getMainHandItem();
        var offHand = data.shooter.getMainHandItem();

        return mainHand == data.gun || (offHand == data.gun
                && GunModifierHelper.isOneHanded(new GunData(mainHand, data.shooter))
                && GunModifierHelper.isOneHanded(new GunData(offHand, data.shooter)));
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
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}