package com.nukateam.cgs.client.hud;

import com.nukateam.cgs.common.data.FuelUtils;
import com.nukateam.cgs.common.ntgl.Attachments;
import com.nukateam.ntgl.client.render.hud.GunHud;
import com.nukateam.ntgl.client.render.hud.GunHudCache;
import com.nukateam.ntgl.common.base.holders.SecondaryAmmoType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.nukateam.cgs.common.ntgl.Attachments.FUEL;

public class SteamGunHud extends GunHud {
    public static final IGuiOverlay HUD = new SteamGunHud();
    public static final int BAR_WIDTH = 50;
    public static final int BAR_HEIGHT = 6;
    public static final int BAR_START_X = 70;
    public static final int BAR_START_Y = 60;

    @Override
    protected void renderAmmoCounter(GuiGraphics graphics, GunHudCache handCache, ItemStack stack, int width, int height) {
        var tag = stack.getOrCreateTag();

//        i f(tag.contains(FUEL, Tag.TAG_INT)){
            var fuel = FuelUtils.getFuel(stack, SecondaryAmmoType.BURNABLE);
            var prog = (fuel / (float)Attachments.MAX_FUEL) * BAR_WIDTH;
            drawBar(graphics, width - BAR_START_X, height - BAR_START_Y, BAR_WIDTH, BAR_HEIGHT, (int)prog);
//        }

        super.renderAmmoCounter(graphics, handCache, stack, width, height);
    }

    protected static void drawBar(GuiGraphics graphics, int x, int y, int width, int height, int prog){
        int color = (double)prog / (double)width < 0.20 ? 0xFF5555FF : 0xFFFFFFFF;

        //TOP
        graphics.fill(x, y, x + width, y + 1, color);
        //BOTTOM
        graphics.fill(x, y + height, x + width, y + height + 1, color);
        //LEFT
        graphics.fill(x, y, x + 1, y + height, color);
        //RIGHT
        graphics.fill(x + width - 1, y, x + width, y + height, color);
        //BAR
        graphics.fill(x, y, x + prog, y + height, color);
    }
}
