package com.nukateam.cgs.common.network;

import com.mrcrayfish.framework.api.FrameworkAPI;
import com.mrcrayfish.framework.api.network.FrameworkNetwork;
import com.mrcrayfish.framework.api.network.MessageDirection;
import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler {
    private static FrameworkNetwork PLAY_CHANNEL;

    public static FrameworkNetwork getPlayChannel() {
        return PLAY_CHANNEL;
    }

    public static void init() {
        PLAY_CHANNEL = FrameworkAPI.createNetworkBuilder(new ResourceLocation(Gunsmithing.MOD_ID, "play"), 1)
                .registerPlayMessage(C2SMessageFuel.class, MessageDirection.PLAY_SERVER_BOUND)
//                .registerPlayMessage(.class, MessageDirection.PLAY_CLIENT_BOUND)
                .build();
    }
}
