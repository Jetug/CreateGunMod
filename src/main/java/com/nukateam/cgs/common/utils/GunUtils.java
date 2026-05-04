package com.nukateam.cgs.common.utils;

import com.nukateam.cgs.common.faundation.registry.CgsComponents;
import com.nukateam.cgs.common.handlers.GunEventHandler;
import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.foundation.init.ModSounds;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GunUtils {
    public static final String COCK = "Cock";
    public static final String AIR = "Air";

    public static boolean isAmmoEven(WeaponData data) {
        return WeaponStateHelper.getAmmoCount(data) % 2 == 0;
    }

    public static void playAttachSound(Player player, float pitch){
        player.level().playSound(null,
                player.getX(),
                player.getY() + 1.0,
                player.getZ(),
                ModSounds.UI_WEAPON_ATTACH.get(),
                SoundSource.PLAYERS, 0.5F,
                pitch);
    }

    public static void giveItemToPlayer(Player player, ItemStack itemStack) {
        if (!player.getInventory().add(itemStack)) {
            player.drop(itemStack, false);
        }
    }

    public static boolean fillFuel(ItemStack gun, Player player, ItemStack fuelStack) {
        var gunData = new WeaponData(gun, player);
        var isSurvival = !player.isCreative();
        var allFuel = WeaponModifierHelper.getAllFuel(gunData);

        for (var fuelType : allFuel){
            var maxFuel = WeaponModifierHelper.getMaxFuel(fuelType.getId(), gunData);
            var currentFuel = FuelUtils.getFuel(gun,fuelType);
            if(fuelType.isAcceptable(fuelStack) && currentFuel < maxFuel) {
                var value = 0;
                if(fuelType == CgsAmmoHolders.AIR){
                    var gunRemaining = FuelUtils.getFuel(fuelStack, fuelType);
                    var tankAir = BacktankUtil.getAir(fuelStack);
                    var airSum = gunRemaining + tankAir;
                    value = Mth.clamp((int)airSum, 0, maxFuel);

                    if(isSurvival) {
                        if (airSum <= maxFuel) {
                            consumeAir(fuelStack, tankAir);
                        } else {
                            var tankRemaining = airSum - maxFuel;
                            setAir(fuelStack, tankRemaining);
                        }
                    }
                } else {
                    value = fuelType.getValue(fuelStack);

                    if(isSurvival) {
                        var returnItem = fuelType.onConsume().apply(fuelStack, 1);
                        returnItem.forEach((item) -> giveItemToPlayer(player, item));
                        fuelStack.shrink(1);
                    }
                }
                FuelUtils.addFuel(gunData, fuelType, value);
                return true;
            }
        }
        return false;
    }

    public static void consumeAir(ItemStack tank, int i) {
        int maxAir = BacktankUtil.maxAir(tank);
        var air = BacktankUtil.getAir(tank);
        var newAir = Math.max(air - i, 0);

        tank.set(AllDataComponents.BACKTANK_AIR, Math.min(newAir, maxAir));
    }

    public static void setAir(ItemStack tank, int newAir) {
        var maxAir = BacktankUtil.maxAir(tank);
        tank.set(AllDataComponents.BACKTANK_AIR, Math.min(newAir, maxAir));
    }

    public static void setCock(ItemStack weapon, int i) {
        weapon.set(CgsComponents.COCK, i);
    }

    public static int getCock(ItemStack weapon) {
        return weapon.getOrDefault(CgsComponents.COCK, 0);
    }

    public static boolean hasAir(WeaponData gunData) {
        var hasAirInGun = FuelUtils.hasFuel(CgsAmmoHolders.AIR.getId(), gunData);
        var hasAirInTank = GunEventHandler.hasAirInTank(gunData);
        return hasAirInGun || hasAirInTank;
    }
}
