package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.ntgl.CgsAmmo;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.nukateam.ntgl.common.event.GunReloadEvent;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolders;
import com.nukateam.ntgl.common.event.GunFireEvent;
import com.nukateam.ntgl.common.event.GunProjectileHitEvent;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.BacktankBlockEntity;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
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

        if(gun.getItem() == CgsWeapons.NAILGUN.get()) {
            if (isUsesFuel(gunData, CgsAmmo.AIR) && !GunUtils.hasAir(gunData) ) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void postShoot(GunFireEvent.Post event) {
        var shooter = event.getEntity();
        var gun = event.getStack();
        var data = new GunData(gun, shooter);
        var fuel = GunModifierHelper.getAllFuel(data);
        if(!event.getEntity().level().isClientSide) {
            if (fuel.contains(AmmoHolders.BURNABLE)) {
                FuelUtils.addFuel(data, AmmoHolders.BURNABLE, -1);
            }
            if (fuel.contains(AmmoHolders.WATER)) {
                FuelUtils.addFuel(data, AmmoHolders.WATER, -5);
            }
            if (fuel.contains(CgsAmmo.AIR)) {
                consumeAir(data);
//            FuelUtils.addFuel(data, CgsAmmo.AIR, -5);
            }
            if (gun.getItem() == CgsWeapons.SHOTGUN.get()) {
                checkCock(data);
            }
        }
    }

    @SubscribeEvent
    public static void postReload(GunReloadEvent.Post event) {
        var gun = event.getStack();
        var shooter = event.getEntity();
        var data = new GunData(gun, shooter);

        if(!event.getEntity().level().isClientSide) {
            if (gun.getItem() == CgsWeapons.SHOTGUN.get()) {
                checkCock(data);
            }
            if (gun.getItem() == CgsWeapons.LAUNCHER.get()) {
                consumeAir(data);
            }
        }
    }

    private static void checkCock(GunData data) {
        var ammoCount = GunStateHelper.getAmmoCount(data);
        var isEven = GunStateHelper.getAmmoCount(data) % 2 == 0;
        var id = 0;

        if (ammoCount != 0) {
            id = isEven ? 2 : 1;
        }

        GunUtils.setCock(data.gun, id);
    }

    public static boolean consumeAir(GunData data) {
        var amount = GunModifierHelper.getFuelAmountPerUse(CgsAmmo.AIR.getId(), data);

        if (amount == 0) return false;

        if (data.shooter instanceof Player player && player.isCreative())
            return true;

        var backtanks = BacktankUtil.getAllWithAir(data.shooter);

        if (!backtanks.isEmpty()) {
            BacktankUtil.consumeAir(data.shooter, backtanks.get(0), amount);
        } else {
            FuelUtils.consumeFuel(CgsAmmo.AIR, data); //.addFuel(data, CgsAmmo.AIR, -5);
        }
        return true;
    }

    public static boolean isUsesFuel(GunData data, AmmoHolder ammo) {
        return GunModifierHelper.getAllFuel(data).stream().anyMatch((i) -> i.getId().equals(ammo.getId()));
    }

    public static boolean hasAirInTank(GunData data) {
        if (data.shooter instanceof Player player && player.isCreative())
            return true;

        var backtanks = BacktankUtil.getAllWithAir(data.shooter);

        if (backtanks.isEmpty())
            return false;

        var cost = GunModifierHelper.getFuelAmountPerUse(CgsAmmo.AIR.getId(), data);
        return BacktankUtil.getAir(backtanks.get(0)) >= cost;
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