
package com.nukateam.cgs.client.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.client.hud.SteamGunHud;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.MouseEvent;

import static com.nukateam.cgs.common.ntgl.Attachments.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class InputEventHandler {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event){
        if(isInGame()) {
            var mc = Minecraft.getInstance();
            var player = mc.player;
            var mainHandItem = player.getMainHandItem();
            var offhandItem = player.getOffhandItem();

            if (event.getButton() == mc.options.keyUse.getKey().getValue()) {
                if (GunModifierHelper.isGun(mainHandItem)) {
                    fillEngine(mainHandItem, offhandItem);
                } else if (GunModifierHelper.isGun(offhandItem)) {
                    fillEngine(offhandItem, mainHandItem);
                }
            }
        }
    }

    private static void fillEngine(ItemStack gun, ItemStack stack) {
        var mc = Minecraft.getInstance();
        var burnTime = ForgeHooks.getBurnTime(stack, null);

        if(burnTime > 0 || stack.getItem() == Items.WATER_BUCKET){
            PacketHandler.getPlayChannel().sendToServer(new C2SMessageFuel());
            mc.options.keyUse.setDown(false);
        }
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
