package com.nukateam.cgs.common.utils;

import com.nukateam.cgs.common.ntgl.CgsFuel;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.init.ModSounds;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

public class GunUtils {
    public static boolean isAmmoEven(ItemStack stack) {
        return Gun.getAmmo(stack) % 2 == 0;
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
        var gunData = new GunData(gun, player);
        var isSurvival = !player.isCreative();
        var allFuel = GunModifierHelper.getFuelTypes(gunData);

        for (var fuelType : allFuel){
            if(fuelType.isAcceptable(fuelStack)) {
                var value = 0;
                var maxFuel = GunModifierHelper.getMaxFuel(gunData, fuelType);
                var currentFuel = FuelUtils.getFuel(gun,fuelType);
                if(currentFuel >= maxFuel) {
                    return false;
                }
                else if(fuelType == FuelType.BURNABLE) {
                    value = ForgeHooks.getBurnTime(fuelStack, null);
                    if(isSurvival) {
                        fuelStack.shrink(1);
                    }
                }
                else if(fuelType == FuelType.WATER) {
                    value = 1000;
                    if(isSurvival) {
                        fuelStack.shrink(1);
                        player.addItem(new ItemStack(Items.BUCKET));
                    }
                }
                else if(fuelType == CgsFuel.AIR){
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
        tag.putFloat("Air", Math.min(newAir, maxAir));
        tank.setTag(tag);
    }

    public static void setAir(ItemStack tank, float newAir) {
        var tag = tank.getOrCreateTag();
        var maxAir = BacktankUtil.maxAir(tank);
        tag.putFloat("Air", Math.min(newAir, maxAir));
        tank.setTag(tag);
    }
}
