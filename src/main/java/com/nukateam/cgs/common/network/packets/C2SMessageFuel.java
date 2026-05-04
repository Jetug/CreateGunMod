package com.nukateam.cgs.common.network.packets;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.nukateam.cgs.common.utils.GunUtils.*;

public record C2SMessageFuel() implements CustomPacketPayload{
    public static final Type<C2SMessageFuel> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Gunsmithing.MOD_ID, "c2s_message_fuel"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SMessageFuel> CODEC = StreamCodec.of(
            (buffer, message) -> encode(message, buffer),
            buffer -> decode(buffer));

    public C2SMessageFuel() {}

    public static void encode(C2SMessageFuel message, FriendlyByteBuf buffer) {}

    public static C2SMessageFuel decode(FriendlyByteBuf buffer) {
        return new C2SMessageFuel();
    }

    public static void handle(C2SMessageFuel message, IPayloadContext supplier) {
        supplier.enqueueWork((() -> {
            var player = supplier.player();
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
//        supplier.setHandled(true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
