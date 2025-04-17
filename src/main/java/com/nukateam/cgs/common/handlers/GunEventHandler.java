package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModGuns;
import com.nukateam.cgs.common.ntgl.CgsFuel;
import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.event.GunFireEvent;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunEventHandler {
    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event) {
        var shooter = event.getEntity();
        var gun = shooter.getItemInHand(event.getHand());

        if(gun.getItem() == ModGuns.NAILGUN.get()) {
            if (!FuelUtils.hasFuel(new GunData(gun, shooter))) {
                event.setCanceled(true);
            }
        }
    }

    public GunEventHandler() {}

    @SubscribeEvent
    public static void postShoot(GunFireEvent.Post event) {
        var shooter = event.getEntity();
        var gun = event.getStack();
        var data = new GunData(gun, shooter);
        var fuel = GunModifierHelper.getFuelTypes(data);

        if(fuel.contains(FuelType.BURNABLE)){
            FuelUtils.addFuel(data, FuelType.BURNABLE, -1);
        }
        if(fuel.contains(FuelType.WATER)){
            FuelUtils.addFuel(data, FuelType.WATER, -5);
        }
        if(fuel.contains(CgsFuel.AIR)){
            FuelUtils.addFuel(data, CgsFuel.AIR, -5);
        }
    }
}