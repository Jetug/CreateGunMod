package com.nukateam.cgs.common.ntgl;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAmmo {
    public static AmmoHolder AIR = AmmoHolder.Builder
            .create(cgsResource("air"))
            .isAcceptable(CgsAmmo::isAir)
            .value((s) -> (int)BacktankUtil.getAir(s))
            .onConsume(CgsAmmo::onConsumeAir)
            .build();

    public static void register() {
        AmmoHolder.registerType(AIR);
    }

    private static Boolean isAir(ItemStack stack) {
        var item = stack.getItem();
        return item instanceof BacktankItem && BacktankUtil.hasAirRemaining(stack);
    }

    private static List<ItemStack> onConsumeAir(ItemStack stack, Integer amount) {
        var tankAir = BacktankUtil.getAir(stack);
        var tank = new ItemStack(stack.getItem());
        GunUtils.setAir(tank, Math.max(0, tankAir - amount));
        GunUtils.consumeAir(tank, amount);
        return List.of(tank);
    }
}
