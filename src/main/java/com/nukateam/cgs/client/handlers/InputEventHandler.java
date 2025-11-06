
package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class InputEventHandler {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event){
        if(isInGame()) {
            var mc = Minecraft.getInstance();
            var player = mc.player;
            var mainHandItem = player.getMainHandItem();
            var offhandItem = player.getOffhandItem();

            if (event.getAction() == GLFW.GLFW_RELEASE && event.getButton() == mc.options.keyUse.getKey().getValue()) {
                if (WeaponModifierHelper.isGun(mainHandItem)) {
                    fillEngine(mainHandItem, offhandItem);
                } else if (WeaponModifierHelper.isGun(offhandItem)) {
                    fillEngine(offhandItem, mainHandItem);
                }
            }
        }
    }

    private static void fillEngine(ItemStack gun, ItemStack fuelStack) {
        var mc = Minecraft.getInstance();
        if(canAcceptFuel(gun, fuelStack)){
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageFuel());
            mc.options.keyUse.setDown(false);
        }
    }

    private static boolean canAcceptFuel(ItemStack gun, ItemStack fuelStack) {
        var mc = Minecraft.getInstance();
        var gunData = new WeaponData(gun, mc.player);
        var allFuel = WeaponModifierHelper.getAllFuel(gunData);
        for (var fuelType: allFuel){
            if (fuelType.isAcceptable(fuelStack) && !FuelUtils.isFull(gunData, fuelType))
                return true;
        }
        return false;
    }

    public static boolean isInGame() {
        var mc = Minecraft.getInstance();
        if (mc.getOverlay() != null)
            return false;
        if (mc.screen != null)
            return false;
        if (!mc.mouseHandler.isMouseGrabbed())
            return false;
        return mc.isWindowActive();
    }
}
