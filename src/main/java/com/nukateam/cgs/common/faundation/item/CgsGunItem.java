package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.data.FuelUtils;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.render.BaseGunRenderer;
import com.nukateam.ntgl.common.base.holders.SecondaryAmmoType;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Lazy;

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

        if (!(entity instanceof LivingEntity livingEntity)) return;

        var fuel = GunModifierHelper.getSecondaryAmmo(new GunData(stack, livingEntity));
        if(!fuel.isEmpty() && isInHand(stack, livingEntity)){
            if(fuel.contains(SecondaryAmmoType.BURNABLE)){
                var value = FuelUtils.getFuel(stack, SecondaryAmmoType.BURNABLE);
                FuelUtils.setFuel(stack, SecondaryAmmoType.BURNABLE, Math.max(0, value - 50));
            }
        }
    }


    private static boolean isInHand(ItemStack stack, LivingEntity livingEntity) {
        var mainHand = livingEntity.getMainHandItem();
        var offHand = livingEntity.getMainHandItem();

        return mainHand == stack || (offHand == stack
                && GunModifierHelper.isOneHanded(new GunData(mainHand, livingEntity))
                && GunModifierHelper.isOneHanded(new GunData(offHand, livingEntity)));
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