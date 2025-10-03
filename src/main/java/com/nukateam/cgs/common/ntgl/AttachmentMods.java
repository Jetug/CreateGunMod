package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.items.ModItems;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.faundation.registry.CgsSounds;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.example.common.registery.GunModifiers;
import com.nukateam.ntgl.common.data.config.ProjectileConfig;
import com.nukateam.ntgl.common.data.holders.*;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.data.GunData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AttachmentMods {
    public static final Scope LONG_SCOPE = Scope.builder()
            .modifiers(GunModifiers.SLOWER_ADS)
            .aimFovModifier(0.25F)
            .overlay()
            .build();

    public static final ProjectileConfig FLAME_PROJECTILE = ProjectileConfig.Builder.create()
            .setProjectileType(CgsProjectileTypes.FIREBALL)
            .setDamage(0.2f)
            .setProjectileLife(10)
            .setProjectileSpeed(1.5f)
            .setProjectileAmount(6)
            .setSpread(15)
            .build();

    public static final ProjectileConfig SUPER_FLAME_PROJECTILE = ProjectileConfig.Builder.create()
            .setProjectileType(CgsProjectileTypes.FIREBALL)
            .setDamage(1f)
            .setProjectileLife(10)
            .setProjectileSpeed(2.5f)
            .setProjectileAmount(8)
            .setSpread(10)
            .build();

    //GENERIC
    public static final IGunModifier STEAM_ENGINE_MODIFIERS = new IGunModifier() {
        @Override
        public ProjectileConfig modifyProjectile(ProjectileConfig value, GunData data) {
            if(data.gun == null) return value;

            if(data.gun.getItem() == CgsWeapons.BLAZEGUN.get()) {
                if(GunStateHelper.getCurrentAmmo(data) == CgsAmmo.BLAZE_CAKE){
                    return SUPER_FLAME_PROJECTILE;
                }
                return FLAME_PROJECTILE;
            }
            return value;
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            if(data.gun == null) return rate;

            if(data.gun.getItem() == CgsWeapons.NAILGUN.get())
                return rate * 2;
            else if(data.gun.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return rate / 2;
            } else if(data.gun.getItem() == CgsWeapons.BLAZEGUN.get()) {
                if(GunStateHelper.getCurrentAmmo(data) == CgsAmmo.BLAZE_CAKE){
                    return 1;
                }
                return 2;
            }
            return rate;
        }

        @Override
        public int modifyFireDelay(int chargeTime, GunData data) {
            if(data.gun == null) return chargeTime;

            if(data.gun.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return 0;
            }
            return IGunModifier.super.modifyFireDelay(chargeTime, data);
        }

        @Override
        public float modifyMovementSpeed(float value, GunData data) {
            if(data.gun == null) return value;

            if(data.gun.getItem() == CgsWeapons.GATLING.get()) {
                return value - 0.2f;
            }
            return IGunModifier.super.modifyMovementSpeed(value, data);
        }

        @Override
        public float modifyDamage(float damage, GunData data) {
            if(data.gun == null) return damage;

            if(data.gun.getItem() == CgsWeapons.NAILGUN.get()) {
                return damage * 2f;
            }
            return IGunModifier.super.modifyDamage(damage, data);
        }

        @Override
        public float recoilModifier(GunData data) {
            if(data.gun != null && data.gun.getItem() == CgsWeapons.NAILGUN.get()) {
                return 2;
            }
            return IGunModifier.super.recoilModifier(data);
        }

        @Override
        public float kickModifier(GunData data) {
            if(data.gun != null && data.gun.getItem() == CgsWeapons.NAILGUN.get()) {
                return 2;
            }
            return IGunModifier.super.kickModifier(data);
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            if(data.gun == null) return spread;

            if(data.gun.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return spread + 8f;
            }
            return spread;
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> fuel, GunData data) {
            if(data.gun == null) return fuel;

            if(data.gun.getItem() == CgsWeapons.BLAZEGUN.get()) {
                return Set.of(AmmoHolders.WATER);
            }
            return Set.of(AmmoHolders.BURNABLE, AmmoHolders.WATER);
        }

        @Override
        public GripType modifyGripType(GripType gripType, GunData data) {
            if(data != null && data.gun != null) {
                if(data.gun.getItem() == CgsWeapons.NAILGUN.get()) {
                    return GripType.TWO_HANDED;
                }
                else if(data.gun.getItem() == CgsWeapons.GATLING.get()) {
                    return getGatlingGripType(gripType, data);
                }
            }
            return IGunModifier.super.modifyGripType(gripType, data);
        }

        @Override
        public ResourceLocation modifyFireSound(ResourceLocation sound, GunData data) {
            if(data.gun != null && data.gun.getItem() == CgsWeapons.NAILGUN.get()){
                return CgsSounds.NAILGUN_FIRE_STEAM.get().getLocation();
            }
            return IGunModifier.super.modifyFireSound(sound, data);
        }

        private GripType getGatlingGripType(GripType gripType, GunData data){
            if(data.shooter != null && data.gun != null) {
                var magazineItem = GunStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, data.gun).getItem();
                var drumItem = AttachmentItems.GATLING_DRUM.get();

                if (magazineItem != drumItem && FuelUtils.hasFuel(data) && data.shooter.hasEffect(MobEffects.DAMAGE_BOOST)) {
                    return GripType.ONE_HANDED;
                }
            }
            return gripType;
        }
    };

    //GATLING
    public static final IGunModifier GATLING_DRUM_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 300;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 2;
        }

        @Override
        public float modifyMovementSpeed(float value, GunData data) {
            return value - 0.1f;
        }
    };

    //FLINTLOCK
    public static final IGunModifier BAYONET_MODIFIERS = new IGunModifier() {
        @Override
        public boolean modifyCanMelee(boolean value, GunData data) {
            return true;
        }

        @Override
        public float modifyMeleeDamage(float value, GunData data) {
            return 10;
        }

        @Override
        public float modifyMeleeAngle(float value, GunData data) {
            if(data.gun == null) return value;

            var barrel = GunStateHelper.getAttachmentItem(AttachmentType.BARREL, data.gun);
            if(barrel.getItem() == AttachmentItems.FLINTLOCK_LONG_BARREL.get())
                return 20;
            else return 120;
        }

        @Override
        public float modifyMeleeDistance(float value, GunData data) {
            if(data.gun == null) return value;

            var barrel = GunStateHelper.getAttachmentItem(AttachmentType.BARREL, data.gun);
            if(barrel.getItem() == AttachmentItems.FLINTLOCK_LONG_BARREL.get())
                return 5;
            else return 3;
        }

        @Override
        public int modifyMeleeDelay(int time, GunData data) {
            return 5;
        }

        @Override
        public int modifyMeleeCooldown(int time, GunData data) {
            return 8;
        }
    };

    //REVOLVER
    public static final IGunModifier BELT_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyReloadTime(int reloadTime, GunData data) {
            return 20;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 12;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, GunData data) {
            return speed * 0.75F;
        }

        @Override
        public LoadingType modifyLoadingType(LoadingType loadingType, GunData data) {
            return LoadingType.PER_CARTRIDGE;
        }
    };

    public static final IGunModifier AUTO_FIRE = new IGunModifier() {
        @Override
        public int modifyFireDelay(int chargeTime, GunData data) {
            return 0;
        }

        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, GunData data) {
            return new LinkedHashSet<>(List.of(FireMode.AUTO, FireMode.SEMI_AUTO));
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 1.5f;
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            return rate / 2;
        }
    };

    public static final IGunModifier LONG_BARREL = new IGunModifier() {
        @Override
        public GripType modifyGripType(GripType gripType, GunData data) {
            if(data.gun != null && data.gun.getItem() == CgsWeapons.REVOLVER.get()){
                if(GunStateHelper.hasAttachmentEquipped(data.gun, AttachmentType.STOCK)){
                    return GripType.TWO_HANDED;
                }
                else return gripType;
            }
            return GripType.TWO_HANDED;
        }

        @Override
        public float modifyDamage(float damage, GunData data) {
            return damage * 1.5f;
        }

        @Override
        public double modifyProjectileSpeed(double speed, GunData data) {
            return speed * 3;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, GunData data) {
            return speed * 0.95;
        }
    };

    public static final IGunModifier STOCK = new IGunModifier() {
        @Override
        public float recoilModifier(GunData data) {
            return 0.1F;
        }

        @Override
        public float kickModifier(GunData data) {
            return 0.1F;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 0.25F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, GunData data) {
            return speed * 0.8F;
        }
    };

    public static final IGunModifier SHOTGUN_MODIFIER = new IGunModifier() {
        @Override
        public int modifyFireRate(int rate, GunData data) {
        if(data.gun == null) return rate;

        var cock = GunUtils.getCock(data.gun);

        if(GunStateHelper.getFireMode(data) == FireMode.MULTI){
            if(cock == 2)
                return 20;
        }
        if(cock == 1)
            return 20;

        return rate;
        }
    };

    public static final IGunModifier SHOTGUN_DRUM_MODIFIER = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 8;
        }

        @Override
        public int modifyReloadTime(int reloadTime, GunData data) {
            return 80;
        }
    };

    public static final IGunModifier SHOTGUN_PUMP_MODIFIER = new IGunModifier() {
        @Override
        public LoadingType modifyLoadingType(LoadingType loadingType, GunData data) {
            return LoadingType.PER_CARTRIDGE;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 10;
        }

        @Override
        public int modifyReloadStart(int reloadTime, GunData data) {
            return 8;
        }

        @Override
        public int modifyReloadTime(int reloadTime, GunData data) {
            return 12;
        }

        @Override
        public int modifyReloadEnd(int reloadTime, GunData data) {
            return 20;
        }

        @Override
        public GripType modifyGripType(GripType gripType, GunData data) {
            return GripType.TWO_HANDED;
        }
    };

    public static final IGunModifier SHOTGUN_LONG_BARREL = new IGunModifier() {
        @Override
        public GripType modifyGripType(GripType gripType, GunData data) {
            return GripType.TWO_HANDED;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 0.5f;
        }
    };

    public static final IGunModifier SHOTGUN_SPREAD_BARREL = new IGunModifier() {
        @Override
        public float modifyDamage(float damage, GunData data) {
            return damage * 1.5f;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 2;
        }


    };

    //NAILGUN
    public static final IGunModifier NAILGUN_SPLIT_BARREL = new IGunModifier() {
        public static final int AMMO_PER_SHOT = 4;

        @Override
        public float modifyDamage(float damage, GunData data) {
            return damage * getProjectileAmount(data);
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 8;
        }

        @Override
        public int modifyProjectileAmount(int amount, GunData data) {
            return getProjectileAmount(data);
        }

        private static int getProjectileAmount(GunData data) {
            return Math.min(AMMO_PER_SHOT, GunStateHelper.getAmmoCount(data));
        }

        @Override
        public int modifyAmmoPerShot(int ammoPerShot, GunData data) {
            return AMMO_PER_SHOT;
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            return (int)(rate * 2.5);
        }
    };

    //HAMMER
    public static final IGunModifier RECIEVER = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 6;
        }

        @Override
        public int modifyAmmoPerShot(int ammoPerShot, GunData data) {
            return 1;
        }

        @Override
        public float modifyMeleeDamage(float damage, GunData data) {
            GunStateHelper.getAmmoCount(data);
            return damage * 2;
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, GunData data) {
            return Set.of(AmmoHolder.getType(ModItems.SHOTGUN_ROUND_BLANK.getId()));
        }
    };

    public static final IGunModifier HAMMER_HEAD = new IGunModifier() {
        @Override
        public int modifyMeleeMaxTargets(int value, GunData data) {
            return 6;
        }

        @Override
        public float modifyMeleeDamage(float damage, GunData data) {
            return damage + 2;
        }

        @Override
        public float modifyMeleeAngle(float value, GunData data) {
            return 80;
        }
    };

    public static final IGunModifier AXE_HEAD = new IGunModifier() {
        @Override
        public int modifyMeleeMaxTargets(int value, GunData data) {
            return 1;
        }

        @Override
        public float modifyMeleeDamage(float damage, GunData data) {
            return damage + 4;
        }
    };

    //LAUNCHER
    public static final IGunModifier BALLISTAZOOKA = new IGunModifier() {
        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, GunData data) {
            return new LinkedHashSet<>(List.of(FireMode.SEMI_AUTO));
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 1;
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            return 15;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return 1;
        }

        @Override
        public double modifyProjectileSpeed(double speed, GunData data) {
            return 40;
        }

        @Override
        public int modifyReloadTime(int reloadTime, GunData data) {
            if(FuelUtils.hasFuel(data)) {
                return 80;
            }
            return 140;
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> secondaryAmmo, GunData data) {
            return Set.of(CgsAmmo.AIR);
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, GunData data) {
            return Set.of(AmmoHolder.getType(ModItems.SPEAR.getId()));
        }

        @Override
        public ResourceLocation modifyFireSound(ResourceLocation sound, GunData data) {
            return CgsSounds.BALLISTA_FIRE.getId();
        }

        @Override
        public boolean modifyAutoReloading(boolean autoReload, GunData data) {
            return true;
        }
    };

    public static final IGunModifier AUTO_LAUNCHER = new IGunModifier() {
        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, GunData data) {
            return new LinkedHashSet<>(List.of(FireMode.AUTO));
        }

        @Override
        public int modifyFuelAmountPerUse(ResourceLocation ammo, int value, GunData data) {
            if(ammo.equals(CgsAmmo.AIR.getId()))
                return  1;
            return value;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 30;
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, GunData data) {
            return Set.of(AmmoHolder.getType(ModItems.SMALL_ROCKET.getId()));
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            return 2;
        }

        @Override
        public int modifyReloadTime(int reloadTime, GunData data) {
            return 100;
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> secondaryAmmo, GunData data) {
            return Set.of(CgsAmmo.AIR);
        }
    };

    public static final IGunModifier BIG_BAYONET = new IGunModifier() {
        @Override
        public boolean modifyCanMelee(boolean value, GunData data) {
            return true;
        }

        @Override
        public float modifyMeleeDamage(float value, GunData data) {
            return 12;
        }
    };
}