package com.nukateam.cgs.common.network.packets;

import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import static com.nukateam.cgs.common.utils.GunUtils.*;

public class C2SMessageFuel {
    public static final StreamCodec<RegistryFriendlyByteBuf, C2SMessageFuel> STREAM_CODEC = StreamCodec.of(
            (buffer, message) -> encode(message, buffer),
            buffer -> decode(buffer));

    public C2SMessageFuel() {}

    public static void encode(C2SMessageFuel message, FriendlyByteBuf buffer) {
    }

    public static C2SMessageFuel decode(FriendlyByteBuf buffer) {
        return new C2SMessageFuel();
    }

    public static void handle(C2SMessageFuel message, MessageContext supplier) {
        supplier.execute((() ->
        {
            var player = supplier.getPlayer();
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
        supplier.setHandled(true);
    }
}
