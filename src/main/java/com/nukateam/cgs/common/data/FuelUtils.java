package com.nukateam.cgs.common.data;

import com.nukateam.ntgl.common.base.holders.SecondaryAmmoType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class FuelUtils {
    public static CompoundTag getOrCreateFuelTag(ItemStack stack) {
        var tag = stack.getOrCreateTag();
        if(tag.contains("Fuel", Tag.TAG_COMPOUND)){
            return tag.getCompound("Fuel");
        }
        return new CompoundTag();
    }

    public static int getFuel(ItemStack stack, SecondaryAmmoType ammo) {
        var fuelTag = getOrCreateFuelTag(stack);

        if(fuelTag.contains(ammo.toString(), Tag.TAG_INT))
            return fuelTag.getInt(ammo.toString());
        return 0;
    }

    public static void setFuel(ItemStack stack, SecondaryAmmoType ammo, int value) {
        var tag = stack.getOrCreateTag();

        var fuelTag = getOrCreateFuelTag(stack);
        fuelTag.putInt(ammo.toString(), value);

        tag.put("Fuel", fuelTag);
        stack.setTag(tag);
    }
}
