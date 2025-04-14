package com.nukateam.cgs.client.hud;

import com.nukateam.ntgl.client.render.hud.GunHud;
import com.nukateam.ntgl.client.render.hud.GunHudCache;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


public class SteamGunHud extends GunHud {
    public static final IGuiOverlay HUD = new SteamGunHud();
    public static final int BAR_WIDTH = 50;
    public static final int BAR_HEIGHT = 6;
    public static final int BAR_START_X = 70;
    public static final int BAR_START_Y = 60;

    @Override
    protected void renderAmmoCounter(GuiGraphics graphics, GunHudCache handCache, ItemStack stack, int width, int height) {
        var mc = Minecraft.getInstance();
        var gunData = new GunData(stack, mc.player);
        var allFuel = GunModifierHelper.getFuelTypes(gunData);
        var barOffsetY = 0;

        for (var fuelType : allFuel) {
            var fuel = FuelUtils.getFuel(stack, fuelType);
            var maxFuel = GunModifierHelper.getMaxFuel(gunData, fuelType);
            var prog = (fuel / (float) maxFuel) * BAR_WIDTH;
            var x = width - BAR_START_X;
            var y = height - BAR_START_Y;
            renderAmmoTypeIcon(graphics, fuelType, x - 19, y - 4 - barOffsetY);
            drawBar(graphics, x, y - barOffsetY, BAR_WIDTH, BAR_HEIGHT, (int) prog);
            barOffsetY += 18;
        }

        super.renderAmmoCounter(graphics, handCache, stack, width, height);
    }

    protected void renderAmmoTypeIcon(GuiGraphics graphics, FuelType ammoType, int x, int y) {
        var icon = ammoType.getIcon();
        graphics.blit(icon, x, y, 0F, 0F, 16, 16, 16, 16);
    }

    protected static void drawBar(GuiGraphics graphics, int x, int y, int width, int height, int prog){
        int color = (double)prog / (double)width < 0.25 ? 0xFFFF5555 : 0xFFFFFFFF;

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
