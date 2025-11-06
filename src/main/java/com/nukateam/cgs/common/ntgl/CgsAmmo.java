package com.nukateam.cgs.common.ntgl;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.nukateam.ntgl.common.registry.AmmoHolders;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAmmo {
    public static AmmoHolder AIR = AmmoHolder.Builder
            .create(cgsResource("air"))
            .isAcceptable(CgsAmmo::isAir)
            .value((s) -> (int)BacktankUtil.getAir(s))
            .onConsume(CgsAmmo::onConsumeAir)
            .build();

    public static final AmmoHolder BURNABLE = AmmoHolder.Builder
            .create(cgsResource("burnable"))
            .isAcceptable(CgsAmmo::isBurnable)
            .value((stack -> ForgeHooks.getBurnTime(stack, null)))
            .onConsume(AmmoHolders::consumeBurnable)
            .build();

    public static final AmmoHolder BLAZE_CAKE = AmmoHolder.Builder
            .create(cgsResource("blaze_cake"))
            .isAcceptable(stack -> stack.getItem() == AllItems.BLAZE_CAKE.get())
            .value((stack -> 20000))
            .descriptionId((s) -> AllItems.BLAZE_CAKE.get().getDescriptionId())
            .build();

    public static void register() {
        AmmoHolder.registerType(AIR);
        AmmoHolder.registerType(BURNABLE);
        AmmoHolder.registerType(BLAZE_CAKE);
    }

    private static boolean isBurnable(ItemStack ammoStack) {
        var burnTime = ForgeHooks.getBurnTime(ammoStack, null);
        return ammoStack.getItem() != AllItems.BLAZE_CAKE.get() && burnTime > 0;
    }

    private static Boolean isAir(ItemStack stack) {
        var item = stack.getItem();
        return item instanceof BacktankItem && BacktankUtil.hasAirRemaining(stack);
    }

    private static List<ItemStack> onConsumeAir(ItemStack tank, Integer amount) {
        var tankAir = BacktankUtil.getAir(tank);
        var newTank = tank.copy();
        GunUtils.setAir(tank, Math.max(0, tankAir - amount));
        GunUtils.consumeAir(newTank, amount);
        return List.of(newTank);
    }
}
