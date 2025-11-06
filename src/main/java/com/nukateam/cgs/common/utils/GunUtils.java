package com.nukateam.cgs.common.utils;

import com.nukateam.cgs.common.handlers.GunEventHandler;
import com.nukateam.cgs.common.ntgl.CgsAmmo;
import com.nukateam.ntgl.common.registry.AmmoHolders;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.foundation.init.ModSounds;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

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

    public static boolean fillFuel(ItemStack gun, Player player, ItemStack fuelStack) {
        var gunData = new WeaponData(gun, player);
        var isSurvival = !player.isCreative();
        var allFuel = WeaponModifierHelper.getAllFuel(gunData);

        for (var fuelType : allFuel){
            var maxFuel = WeaponModifierHelper.getMaxFuel(fuelType.getId(), gunData);
            var currentFuel = FuelUtils.getFuel(gun,fuelType);
            if(fuelType.isAcceptable(fuelStack) && currentFuel < maxFuel) {
                var value = 0;
                if(fuelType == AmmoHolders.BURNABLE) {
                    value = ForgeHooks.getBurnTime(fuelStack, null);
                    if(isSurvival) {
                        fuelStack.shrink(1);
                    }
                }
                else if(fuelType == AmmoHolders.WATER) {
                    value = 1000;
                    if(isSurvival) {
                        fuelStack.shrink(1);
                        player.addItem(new ItemStack(Items.BUCKET));
                    }
                }
                else if(fuelType == CgsAmmo.AIR){
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
                }
                FuelUtils.addFuel(gunData, fuelType, value);
                return true;
            }
        }
        return false;
    }

    public static void consumeAir(ItemStack tank, float i) {
        var tag = tank.getOrCreateTag();
        int maxAir = BacktankUtil.maxAir(tank);
        float air = BacktankUtil.getAir(tank);
        float newAir = Math.max(air - i, 0);
        tag.putFloat(AIR, Math.min(newAir, maxAir));
        tank.setTag(tag);
    }

    public static void setAir(ItemStack tank, float newAir) {
        var tag = tank.getOrCreateTag();
        var maxAir = BacktankUtil.maxAir(tank);
        tag.putFloat(AIR, Math.min(newAir, maxAir));
        tank.setTag(tag);
    }

//    public static void consumeAir(ItemStack tank, int amount) {
//        var tankAir = BacktankUtil.getAir(tank);
//        GunUtils.setAir(tank, Math.max(0, tankAir - amount));
//    }

    public static void setCock(ItemStack weapon, int i) {
        var tag = weapon.getOrCreateTag();
        tag.putInt(COCK, i);
    }

    public static int getCock(ItemStack weapon) {
        var tag = weapon.getOrCreateTag();
        return tag.getInt(COCK);
    }

    public static boolean hasAir(WeaponData gunData) {
        var hasAirInGun = FuelUtils.hasFuel(CgsAmmo.AIR.getId(), gunData);
        var hasAirInTank = GunEventHandler.hasAirInTank(gunData);
        return hasAirInGun || hasAirInTank;
    }
}
