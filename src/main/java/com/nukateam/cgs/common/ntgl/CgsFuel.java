package com.nukateam.cgs.common.ntgl;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import net.minecraft.world.item.ItemStack;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsFuel {
    public static AmmoHolder AIR = AmmoHolder.Builder.create(cgsResource("air"))
            .isAcceptable(CgsFuel::isAir)
            .build();

    static {
        AmmoHolder.registerType(AIR);
    }

    private static Boolean isAir(ItemStack stack) {
        var item = stack.getItem();
        return item instanceof BacktankItem;
    }
}
