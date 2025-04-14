package com.nukateam.cgs.common.network.packets;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.cgs.common.ntgl.CgsFuel;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

public class C2SMessageFuel extends PlayMessage<C2SMessageFuel> {
    public C2SMessageFuel() {}

    @Override
    public void encode(C2SMessageFuel message, FriendlyByteBuf buffer) {
//        buffer.writeBoolean(message.aiming);
    }

    @Override
    public C2SMessageFuel decode(FriendlyByteBuf buffer) {
        return new C2SMessageFuel();
    }

    @Override
    public void handle(C2SMessageFuel message, MessageContext supplier) {
        supplier.execute((() ->
        {
            var player = supplier.getPlayer();
            if (player != null && !player.isSpectator()) {
                var mainHandItem = player.getMainHandItem();
                var offhandItem = player.getOffhandItem();

                if (GunModifierHelper.isGun(mainHandItem)) {
                    fillEngine(new GunData(mainHandItem, player), offhandItem);
                }
                else if (GunModifierHelper.isGun(offhandItem)) {
                    fillEngine(new GunData(offhandItem, player), mainHandItem);
                }
            }
        }));
        supplier.setHandled(true);
    }

    private static void fillEngine(GunData gunData, ItemStack fuelStack) {
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
            }
        }
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
