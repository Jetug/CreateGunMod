package com.nukateam.cgs.common.network;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = Gunsmithing.MOD_ID)
public class PacketHandler {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToServer(C2SMessageFuel.TYPE, C2SMessageFuel.CODEC, C2SMessageFuel::handle);
    }
}
