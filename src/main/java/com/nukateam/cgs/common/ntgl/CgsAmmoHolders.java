package com.nukateam.cgs.common.ntgl;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAmmoHolders {
    public static AmmoHolder AIR = AmmoHolder.Builder
            .create(cgsResource("air"))
            .isAcceptable(CgsAmmoHolders::isAir)
            .value((s) -> (int)BacktankUtil.getAir(s))
            .onConsume(CgsAmmoHolders::onConsumeAir)
            .build();

    public static final AmmoHolder WATER = AmmoHolder.Builder
            .create(cgsResource("water"))
            .isAcceptable(CgsAmmoHolders::isWater)
            .value((stack -> 1000))
            .onConsume(CgsAmmoHolders::consumeWater)
            .build();

    public static final AmmoHolder BURNABLE = AmmoHolder.Builder
            .create(cgsResource("burnable"))
            .isAcceptable(CgsAmmoHolders::isBurnable)
            .value((stack -> ForgeHooks.getBurnTime(stack, null)))
            .onConsume(CgsAmmoHolders::consumeBurnable)
            .build();


    public static final AmmoHolder BLAZE_CAKE = AmmoHolder.Builder
            .create(cgsResource("blaze_cake"))
            .isAcceptable(stack -> stack.getItem() == AllItems.BLAZE_CAKE.get())
            .value((stack -> 20000))
            .descriptionId((s) -> AllItems.BLAZE_CAKE.get().getDescriptionId())
            .build();


    public static void register() {
        AmmoHolder.registerType(AIR);
        AmmoHolder.registerType(WATER);
        AmmoHolder.registerType(BURNABLE);
        AmmoHolder.registerType(BLAZE_CAKE);
    }

    private static Boolean isAir(ItemStack stack) {
        var item = stack.getItem();
        return item instanceof BacktankItem && BacktankUtil.hasAirRemaining(stack);
    }

    public static boolean isWater(ItemStack ammoStack) {
        return ammoStack.getItem() == Items.WATER_BUCKET
                || ammoStack.getItem() == CgsItems.WATER_CONTAINER.get();
    }

    private static boolean isBurnable(ItemStack ammoStack) {
        var burnTime = ForgeHooks.getBurnTime(ammoStack, null);
        return ammoStack.getItem() != AllItems.BLAZE_CAKE.get() && burnTime > 0;
    }

    private static List<ItemStack> onConsumeAir(ItemStack tank, Integer amount) {
        var tankAir = BacktankUtil.getAir(tank);
        var newTank = tank.copy();
        GunUtils.setAir(tank, Math.max(0, tankAir - amount));
        GunUtils.consumeAir(newTank, amount);
        return List.of(newTank);
    }

    public static List<ItemStack> consumeWater(ItemStack stack, Integer i) {
        if (stack.getItem() == Items.WATER_BUCKET) {
            return List.of(new ItemStack(Items.BUCKET));
        }
        else if(stack.getItem() == CgsItems.WATER_CONTAINER.get()){
            return List.of(new ItemStack(CgsItems.EMPTY_CONTAINER.get()));
        }
        return List.of();
    }

    public static List<ItemStack> consumeBurnable(ItemStack stack, Integer i) {
        if (stack.getItem() == Items.LAVA_BUCKET) {
            return List.of(new ItemStack(Items.BUCKET));
        }
        else if(stack.getItem() == CgsItems.LAVA_CONTAINER.get()){
            return List.of(new ItemStack(CgsItems.EMPTY_CONTAINER.get()));
        }
        return List.of();
    }

}
