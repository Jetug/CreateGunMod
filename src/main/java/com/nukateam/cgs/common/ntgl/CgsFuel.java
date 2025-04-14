package com.nukateam.cgs.common.ntgl;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.world.item.ItemStack;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsFuel {
    public static FuelType AIR = new FuelType(cgsResource("air"), CgsFuel::isAir);

    static {
        FuelType.registerType(AIR);
    }

    private static Boolean isAir(ItemStack stack) {
        var item = stack.getItem();

        var copperTank = AllItems.COPPER_BACKTANK.get();
        var netherTank = AllItems.NETHERITE_BACKTANK.get();

        return item == copperTank || item == netherTank;
    }
}
