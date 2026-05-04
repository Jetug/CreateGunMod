package com.nukateam.cgs.common.network;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class PacketHandler {
//    private static FrameworkNetwork PLAY_CHANNEL;
//
//    public static FrameworkNetwork getPlayChannel() {
//        return PLAY_CHANNEL;
//    }
//
//    public static void init() {
//        PLAY_CHANNEL = FrameworkAPI.createNetworkBuilder(ResourceLocation.fromNamespaceAndPath(Gunsmithing.MOD_ID, "play"), 1)
//                .registerPlayMessage(C2SMessageFuel.class, MessageDirection.PLAY_SERVER_BOUND)
////                .registerPlayMessage(.class, MessageDirection.PLAY_CLIENT_BOUND)
//                .build();
//    }

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(C2SMessageFuel.TYPE, C2SMessageFuel.CODEC, C2SMessageFuel::handle);
    }
}
