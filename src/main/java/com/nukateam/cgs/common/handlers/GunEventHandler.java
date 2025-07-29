package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModGuns;
import com.nukateam.cgs.common.ntgl.CgsFuel;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.data.holders.FuelType;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.event.GunFireEvent;
import com.nukateam.ntgl.common.event.GunProjectileHitEvent;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankBlockEntity;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.phys.HitResult.Type.BLOCK;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunEventHandler {
    @SubscribeEvent
    public static void preShoot(GunFireEvent.Pre event) {
        var shooter = event.getEntity();
        var gun = shooter.getItemInHand(event.getHand());
        var gunData = new GunData(gun, shooter);
        var hasExtendoGrip = shooter.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();

        if(hasExtendoGrip && !GunModifierHelper.isOneHanded(gunData)){
            event.setCanceled(true);
        }

        if(gun.getItem() == ModGuns.NAILGUN.get()) {
            if (!FuelUtils.hasFuel(gunData)) {
                event.setCanceled(true);
            }
        }
    }

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

    @SubscribeEvent
    public static void onHit(GunProjectileHitEvent event) {
        var shooter = event.getProjectile().getShooter();
        var hitResult = event.getRayTrace();
        var level = shooter.level();
        var pos = hitResult.getLocation();
        var type = hitResult.getType();

        if(type == BLOCK){
            var blockPos = new BlockPos((int)pos.x, (int)pos.y, (int)pos.z);
            var blockState = level.getBlockState(blockPos);
            var blockEntity = level.getBlockEntity(blockPos);

            if(blockState.getBlock() == AllBlocks.COPPER_BACKTANK.get()
                    && blockEntity instanceof BacktankBlockEntity tankEntity){
                var airLevel = tankEntity.getAirLevel();
                int max = BacktankUtil.maxAir(0);
                 if(airLevel >= max / 3){
                     explodeOnHit(level, blockPos);
                 }
            }
        }
    }

    public static void explodeOnHit(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            level.destroyBlock(pos, false);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 6.0f, Level.ExplosionInteraction.NONE);
        }
    }
}