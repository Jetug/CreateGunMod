package com.nukateam.cgs.common.network.packets;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.network.FriendlyByteBuf;

import static com.nukateam.cgs.common.utils.GunUtils.*;

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
                    fillFuel(mainHandItem, player, offhandItem);
                }
                else if (GunModifierHelper.isGun(offhandItem)) {
                    fillFuel(offhandItem, player, mainHandItem);
                }
            }
        }));
        supplier.setHandled(true);
    }
}
