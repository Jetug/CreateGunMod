package com.nukateam.cgs.common.network;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import com.nukateam.ntgl.modules.network.ForgeNetwork;
import net.minecraft.resources.ResourceLocation;
import com.nukateam.ntgl.modules.network.IMessage;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int packetId = 0;
    public static final SimpleChannel PLAY_CHANNEL = NetworkRegistry.ChannelBuilder.named(ResourceLocation.tryBuild(Gunsmithing.MOD_ID, "play"))
            .networkProtocolVersion(() -> "1")
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static ForgeNetwork getPlayChannel() {
        return new ForgeNetwork(PLAY_CHANNEL);
    }

    public static void init() {
        registerPlayMessage(C2SMessageFuel.class, NetworkDirection.PLAY_TO_SERVER);
    }

    public static <T extends IMessage<T>> void registerPlayMessage(Class<T> messageClass, @Nullable NetworkDirection direction) {
        try {
            var constructor = messageClass.getDeclaredConstructor();
            var message = constructor.newInstance();

            PLAY_CHANNEL.registerMessage(packetId++,
                    messageClass, message::encode, message::decode,
                    (msg, messageContext) -> {
                        message.handle(msg, messageContext.get());
                    },
                    Optional.ofNullable(direction));

        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("The message %s is missing an empty parameter constructor", messageClass.getName()), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Unable to access the constructor of %s. Make sure the constructor is public.", messageClass.getName()), e);
        } catch (InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}
