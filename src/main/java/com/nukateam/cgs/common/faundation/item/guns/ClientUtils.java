package com.nukateam.cgs.common.faundation.item.guns;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientUtils {
    public static Player getPlayer(){
        return Minecraft.getInstance().player;
    }
}
