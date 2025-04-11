package com.nukateam.cgs.common.network.packets;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.nukateam.cgs.common.data.FuelUtils;
import com.nukateam.ntgl.common.base.holders.SecondaryAmmoType;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

import static com.nukateam.cgs.common.ntgl.Attachments.*;

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
                    fillEngine(mainHandItem, offhandItem);
                } else if (GunModifierHelper.isGun(offhandItem)) {
                    fillEngine(offhandItem, mainHandItem);
                }
            }
        }));
        supplier.setHandled(true);
    }

    private static void fillEngine(ItemStack gun, ItemStack stack) {
        var burnTime = ForgeHooks.getBurnTime(stack, null);

        if(burnTime > 0){
            addFuel(gun, SecondaryAmmoType.BURNABLE, MAX_FUEL, burnTime);
        } else if(stack.getItem() == Items.WATER_BUCKET){
            addFuel(gun, SecondaryAmmoType.WATER, MAX_WATER, 1000);
        }
    }

    private static void addFuel(ItemStack stack, SecondaryAmmoType ammoType, int maxValue, int value) {
        var fuel = FuelUtils.getFuel(stack, ammoType);
        FuelUtils.setFuel(stack, ammoType, Math.min(maxValue, fuel + value));
    }
}
