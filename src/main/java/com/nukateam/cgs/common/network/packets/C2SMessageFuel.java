package com.nukateam.cgs.common.network.packets;

import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.network.FriendlyByteBuf;
import com.nukateam.ntgl.modules.network.IMessage;
import net.minecraftforge.network.NetworkEvent;

import static com.nukateam.cgs.common.utils.GunUtils.*;

public class C2SMessageFuel implements IMessage<C2SMessageFuel> {
    public C2SMessageFuel() {}

    @Override
    public void encode(C2SMessageFuel message, FriendlyByteBuf buffer) {
    }

    @Override
    public C2SMessageFuel decode(FriendlyByteBuf buffer) {
        return new C2SMessageFuel();
    }

    @Override
    public void handle(C2SMessageFuel message, NetworkEvent.Context supplier) {
        supplier.enqueueWork((() ->
        {
            var player = supplier.getSender();
            if (player != null && !player.isSpectator()) {
                var mainHandItem = player.getMainHandItem();
                var offhandItem = player.getOffhandItem();

                if (WeaponModifierHelper.isGun(mainHandItem)) {
                    fillFuel(mainHandItem, player, offhandItem);
                }
                else if (WeaponModifierHelper.isGun(offhandItem)) {
                    fillFuel(offhandItem, player, mainHandItem);
                }
            }
        }));
        supplier.setPacketHandled(true);
    }
}
