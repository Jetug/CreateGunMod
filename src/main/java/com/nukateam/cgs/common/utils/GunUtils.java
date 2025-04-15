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

    public static boolean fillFuel(GunData gunData, ItemStack fuelStack) {
        var player = (Player)gunData.shooter;
        var allFuel = GunModifierHelper.getFuelTypes(gunData);

//        if(allFuel == null) return;
        for (var fuelType : allFuel){
            if(fuelType.isAcceptable(fuelStack)) {
                var value = 0;
                if(fuelType == FuelType.BURNABLE) {
                    var burnTime = ForgeHooks.getBurnTime(fuelStack, null);
                    value = burnTime;
                    if(!player.isCreative())
                        fuelStack.shrink(1);
                }
                else if(fuelType == FuelType.WATER) {
                    value = 1000;
                    if(!player.isCreative())
                        fuelStack = new ItemStack(Items.BUCKET);
                }
                else if(fuelType == CgsFuel.AIR){
                    var gunRemaining = FuelUtils.getFuel(fuelStack, fuelType);
                    var tankAir = BacktankUtil.getAir(fuelStack);
                    var maxTankAir = BacktankUtil.maxAir(fuelStack);
                    var maxFuel = GunModifierHelper.getMaxFuel(gunData, fuelType);

                    var airSum = gunRemaining + tankAir;
                    value = Mth.clamp((int)airSum, 0, maxFuel);

                    if(!player.isCreative()) {
                        if (airSum <= maxFuel) {
                            consumeAir(fuelStack, tankAir);
                        } else {
                            var tankRemaining = airSum - maxFuel;
                            consumeAir(fuelStack, maxTankAir - tankRemaining);
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
}
