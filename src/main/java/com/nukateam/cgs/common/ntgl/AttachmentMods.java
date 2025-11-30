package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.faundation.item.guns.HammerItem;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.faundation.registry.CgsSounds;
import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.example.common.registery.WeaponModifiers;
import com.nukateam.ntgl.common.data.config.weapon.ProjectileConfig;
import com.nukateam.ntgl.common.data.holders.*;
import com.nukateam.ntgl.common.registry.AmmoHolders;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.data.WeaponData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AttachmentMods {
    public static final Scope LONG_SCOPE = Scope.builder()
            .modifiers(WeaponModifiers.SLOWER_ADS)
            .aimFovModifier(0.25F)
            .overlay()
            .build();

    public static final ProjectileConfig FLAME_PROJECTILE = ProjectileConfig.Builder.create()
            .setProjectileType(CgsProjectileTypes.FIREBALL)
            .setDamage(1f)
            .setProjectileLife(10)
            .setProjectileSpeed(1.5f)
            .setProjectileAmount(6)
            .setSpread(15)
            .build();

    public static final ProjectileConfig SUPER_FLAME_PROJECTILE = ProjectileConfig.Builder.create()
            .setProjectileType(CgsProjectileTypes.FIREBALL)
            .setDamage(3f)
            .setProjectileLife(10)
            .setProjectileSpeed(2.5f)
            .setProjectileAmount(8)
            .setSpread(10)
            .build();

    //GENERIC
    public static final IWeaponModifier STEAM_ENGINE_MODIFIERS = new IWeaponModifier() {
        @Override
        public WeaponAction modifyWeaponAction(WeaponAction value, WeaponData data) {
            if(data.weapon.getItem() == CgsWeapons.BLAZEGUN.get() && data.weaponMode == WeaponMode.SECONDARY){
                return WeaponAction.SHOT;
            }

            return value;
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            if(data.weapon == null) return rate;

            if(data.weapon.getItem() == CgsWeapons.NAILGUN.get())
                return rate * 2;
            else if(data.weapon.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return rate / 2;
            }
            else if(data.weapon.getItem() == CgsWeapons.BLAZEGUN.get()) {
                if(WeaponStateHelper.getCurrentAmmo(data) == CgsAmmoHolders.BLAZE_CAKE){
                    return rate / 2;
                }
            }
            return rate;
        }

        @Override
        public int modifyFireDelay(int chargeTime, WeaponData data) {
            if(data.weapon == null) return chargeTime;

            if(data.weapon.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return 0;
            }
            return IWeaponModifier.super.modifyFireDelay(chargeTime, data);
        }

        @Override
        public float modifyMovementSpeed(float value, WeaponData data) {
            if(data.weapon == null) return value;

            if(data.weapon.getItem() == CgsWeapons.GATLING.get()) {
                return value - 0.2f;
            }
            return IWeaponModifier.super.modifyMovementSpeed(value, data);
        }

        @Override
        public ProjectileConfig modifyProjectile(ProjectileConfig value, WeaponData data) {
            if(data.weapon == null) return value;

            if(data.weapon.getItem() == CgsWeapons.BLAZEGUN.get() && data.weaponMode == WeaponMode.SECONDARY) {
                if(WeaponStateHelper.getCurrentAmmo(data) == CgsAmmoHolders.BLAZE_CAKE){
                    return SUPER_FLAME_PROJECTILE;
                }
                return FLAME_PROJECTILE;
            }
            return value;
        }

        @Override
        public float modifyProjectileDamage(float damage, ResourceLocation ammo, WeaponData data) {
            if(data.weapon == null) return damage;

            if(data.weapon.getItem() == CgsWeapons.NAILGUN.get()) {
                return damage * 2f;
            }
            return damage;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            if(data.weapon == null) return spread;

            if(data.weapon.getItem() == CgsWeapons.GATLING.get() && FuelUtils.hasFuel(data)) {
                return spread + 8f;
            }
            return spread;
        }

        @Override
        public float recoilModifier(WeaponData data) {
            if(data.weapon != null && data.weapon.getItem() == CgsWeapons.NAILGUN.get()) {
                return 2;
            }
            return IWeaponModifier.super.recoilModifier(data);
        }

        @Override
        public float kickModifier(WeaponData data) {
            if(data.weapon != null && data.weapon.getItem() == CgsWeapons.NAILGUN.get()) {
                return 2;
            }
            return IWeaponModifier.super.kickModifier(data);
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> fuel, WeaponData data) {
            if(data.weapon == null) return fuel;

            if(data.weapon.getItem() == CgsWeapons.BLAZEGUN.get()) {
                return Set.of(CgsAmmoHolders.WATER);
            }
            return Set.of(CgsAmmoHolders.BURNABLE, CgsAmmoHolders.WATER);
        }

        @Override
        public GripType modifyGripType(GripType gripType, WeaponData data) {
            if(data != null && data.weapon != null) {
                if(data.weapon.getItem() == CgsWeapons.NAILGUN.get()) {
                    return GripType.TWO_HANDED;
                }
                else if(data.weapon.getItem() == CgsWeapons.GATLING.get()) {
                    if(data.weapon.getItem() == CgsWeapons.GATLING.get()) {
                        return GripType.ONE_HANDED;
                    }
                    else return gripType;
                }
            }
            return IWeaponModifier.super.modifyGripType(gripType, data);
        }

        @Override
        public ResourceLocation modifyFireSound(ResourceLocation sound, WeaponData data) {
            if(data.weapon != null && data.weapon.getItem() == CgsWeapons.NAILGUN.get()){
                return CgsSounds.NAILGUN_FIRE_STEAM.get().getLocation();
            }
            return IWeaponModifier.super.modifyFireSound(sound, data);
        }

        @Override
        public boolean modifyOneHanded(boolean value, WeaponData data) {
            if(data.weapon.getItem() == CgsWeapons.GATLING.get()) {
                return getGatlingOneHanded(data);
            }
            return value;
        }

        private boolean getGatlingOneHanded(WeaponData data){
            if(data.wielder != null && data.weapon != null) {
                var magazineItem = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, data.weapon).getItem();
                var drumItem = CgsAttachments.GATLING_DRUM.get();
                var hasStrengthEffect = data.wielder.hasEffect(MobEffects.DAMAGE_BOOST);
                return magazineItem != drumItem && FuelUtils.hasFuel(data) && hasStrengthEffect;
            }
            return false;
        }
    };

    //GATLING
    public static final IWeaponModifier GATLING_DRUM_MODIFIERS = new IWeaponModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 300;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 2;
        }

        @Override
        public float modifyMovementSpeed(float value, WeaponData data) {
            return value - 0.1f;
        }
    };

    //FLINTLOCK
    public static final IWeaponModifier BAYONET_MODIFIERS = new IWeaponModifier() {
        @Override
        public WeaponAction modifyWeaponAction(WeaponAction value, WeaponData data) {
            if(data.weaponMode == WeaponMode.ADDITIONAL){
                return WeaponAction.MELEE;
            }
            return value;
        }

        @Override
        public float modifyMeleeDamage(float value, WeaponData data) {
            return 10;
        }

        @Override
        public float modifyMeleeAngle(float value, WeaponData data) {
            if(data.weapon == null) return value;

            var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, data.weapon);
            if(barrel.getItem() == CgsAttachments.FLINTLOCK_LONG_BARREL.get())
                return 20;
            else return 120;
        }

        @Override
        public float modifyMeleeDistance(float value, WeaponData data) {
            if(data.weapon == null) return value;

            var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, data.weapon);
            if(barrel.getItem() == CgsAttachments.FLINTLOCK_LONG_BARREL.get())
                return 5;
            else return 3;
        }

        @Override
        public int modifyMeleeDelay(int time, WeaponData data) {
            return 5;
        }

        @Override
        public int modifyMeleeCooldown(int time, WeaponData data) {
            return 8;
        }
    };

    //REVOLVER
    public static final IWeaponModifier BELT_MODIFIERS = new IWeaponModifier() {
        @Override
        public int modifyReloadTime(int reloadTime, WeaponData data) {
            return 20;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 12;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, WeaponData data) {
            return speed * 0.75F;
        }

        @Override
        public LoadingType modifyLoadingType(LoadingType loadingType, WeaponData data) {
            return LoadingType.PER_CARTRIDGE;
        }
    };

    public static final IWeaponModifier AUTO_FIRE = new IWeaponModifier() {
        @Override
        public int modifyFireDelay(int chargeTime, WeaponData data) {
            return 0;
        }

        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, WeaponData data) {
            return new LinkedHashSet<>(List.of(FireMode.AUTO, FireMode.SEMI_AUTO));
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 1.5f;
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            return rate / 2;
        }
    };

    public static class BlunderbussBarrel implements IWeaponModifier {
        @Override
        public int modifyProjectileAmount(int amount, WeaponData data) {
            return 20;
        }

        @Override
        public float modifyProjectileDamage(float damage, ResourceLocation ammo, WeaponData data) {
            return 50;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return 50;
        }
    };

    public static final IWeaponModifier LONG_BLUNDERBUSS_BARREL = new BlunderbussBarrel() {
        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return 25;
        }
    };

    public static final IWeaponModifier BLUNDERBUSS_BARREL = new BlunderbussBarrel();

    public static final IWeaponModifier LONG_BARREL = new IWeaponModifier() {
        @Override
        public GripType modifyGripType(GripType gripType, WeaponData data) {
            if(data.weapon != null && data.weapon.getItem() == CgsWeapons.REVOLVER.get()){
                if(WeaponStateHelper.hasAttachmentEquipped(data.weapon, AttachmentType.STOCK)){
                    return GripType.TWO_HANDED;
                }
                else return gripType;
            }
            return GripType.TWO_HANDED;
        }

        @Override
        public boolean modifyOneHanded(boolean value, WeaponData data) {
            return false;
        }

        @Override
        public float modifyProjectileDamage(float damage, ResourceLocation ammo, WeaponData data) {
            return damage * 1.5f;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, WeaponData data) {
            return speed * 0.95;
        }
    };

    public static final IWeaponModifier MORTAR_BARREL = new IWeaponModifier() {
        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> item, WeaponData data) {
            return Set.of(AmmoHolder.getType(CgsWeapons.GRENADE.getId()));
        }

        @Override
        public int modifyMaxFuel(ResourceLocation ammo, int max, WeaponData data) {
            return 1;
        }

        @Override
        public boolean modifyIsFuelMandatory(ResourceLocation ammo, boolean value, WeaponData data) {
            return true;
        }
    };

    public static final IWeaponModifier REVOLVING_CHAMBERS = new IWeaponModifier() {
        @Override
        public int modifyReloadTime(int value, WeaponData data) {
            return 50;
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            return 16;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 8;
        }
    };

    public static final IWeaponModifier STOCK = new IWeaponModifier() {
        @Override
        public float recoilModifier(WeaponData data) {
            return 0.1F;
        }

        @Override
        public float kickModifier(WeaponData data) {
            return 0.1F;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 0.25F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed, WeaponData data) {
            return speed * 0.8F;
        }
    };

    public static final IWeaponModifier SHOTGUN_MODIFIER = new IWeaponModifier() {
        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            if(data.weapon == null) return rate;

            var cock = GunUtils.getCock(data.weapon);

            if(WeaponStateHelper.getFireMode(data) == FireMode.MULTI){
                if(cock == 2)
                    return 20;
            }
            if(cock == 1)
                return 20;

            return rate;
        }
    };

    public static final IWeaponModifier SHOTGUN_DRUM_MODIFIER = new IWeaponModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 8;
        }

        @Override
        public int modifyReloadTime(int reloadTime, WeaponData data) {
            return 80;
        }
    };

    public static final IWeaponModifier SHOTGUN_PUMP_MODIFIER = new IWeaponModifier() {
        @Override
        public LoadingType modifyLoadingType(LoadingType loadingType, WeaponData data) {
            return LoadingType.PER_CARTRIDGE;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 10;
        }

        @Override
        public int modifyReloadStart(int reloadTime, WeaponData data) {
            return 8;
        }

        @Override
        public int modifyReloadTime(int reloadTime, WeaponData data) {
            return 12;
        }

        @Override
        public int modifyReloadEnd(int reloadTime, WeaponData data) {
            return 20;
        }

        @Override
        public GripType modifyGripType(GripType gripType, WeaponData data) {
            return GripType.TWO_HANDED;
        }

        @Override
        public boolean modifyOneHanded(boolean value, WeaponData data) {
            return false;
        }
    };

    public static final IWeaponModifier SHOTGUN_LONG_BARREL = new IWeaponModifier() {
        @Override
        public GripType modifyGripType(GripType gripType, WeaponData data) {
            return GripType.TWO_HANDED;
        }

        @Override
        public boolean modifyOneHanded(boolean value, WeaponData data) {
            return false;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 0.5f;
        }
    };

    public static final IWeaponModifier SHOTGUN_SPREAD_BARREL = new IWeaponModifier() {
        @Override
        public float modifyProjectileDamage(float damage, ResourceLocation ammo, WeaponData data) {
            return damage * 1.5f;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 2;
        }
    };

    //NAILGUN
    public static final IWeaponModifier NAILGUN_SPLIT_BARREL = new IWeaponModifier() {
        public static final int AMMO_PER_SHOT = 4;

        @Override
        public float modifyProjectileDamage(float damage, ResourceLocation ammo, WeaponData data) {
            return damage * getProjectileAmount(data);
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return spread * 8;
        }

        @Override
        public int modifyProjectileAmount(int amount, WeaponData data) {
            return getProjectileAmount(data);
        }

        private static int getProjectileAmount(WeaponData data) {
            return Math.min(AMMO_PER_SHOT, WeaponStateHelper.getAmmoCount(data));
        }

        @Override
        public int modifyAmmoPerShot(int ammoPerShot, WeaponData data) {
            return AMMO_PER_SHOT;
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            return (int)(rate * 2.5);
        }
    };

    //HAMMER
    public static final IWeaponModifier RECIEVER = new IWeaponModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 6;
        }

        @Override
        public int modifyAmmoPerShot(int ammoPerShot, WeaponData data) {
            return 1;
        }

        @Override
        public float modifyMeleeDamage(float damage, WeaponData data) {
            WeaponStateHelper.getAmmoCount(data);
            return damage * 2;
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, WeaponData data) {
            return Set.of(AmmoHolder.getType(CgsAmmo.SHOTGUN_ROUND_BLANK.getId()));
        }
    };

    private static float getHeadMeleeDamage(float damage, ItemStack attachment) {
        if(attachment.getItem() instanceof HammerHeadItem headItem) {
            return damage + 2 + headItem.getTier().getAttackDamageBonus() * 2;
        }
        return damage;
    }

    private static class HeadModifier implements IWeaponModifier {
        @Override
        public WeaponAction modifyWeaponAction(WeaponAction value, WeaponData data) {
            if(data.weaponMode == WeaponMode.SECONDARY){
                if(HammerItem.isPowered(data)){
                    return WeaponAction.MELEE;
                }
            }
            return IWeaponModifier.super.modifyWeaponAction(value, data);
        }

        @Override
        public float modifyMeleeDamage(float damage, WeaponData data) {
            if (data.weapon != null) {
                damage = getHeadMeleeDamage(damage, data.attachment);

                if(data.weaponMode == WeaponMode.SECONDARY && HammerItem.isPowered(data)){
                    damage += 6;
                }

                return damage;
            } else {
                return 1;
            }
        }
    }

    public static final IWeaponModifier HAMMER_HEAD = new HeadModifier() {
        @Override
        public int modifyMeleeMaxTargets(int value, WeaponData data) {
            return 6;
        }

        @Override
        public float modifyMeleeAngle(float value, WeaponData data) {
            return 80;
        }
    };

    public static final IWeaponModifier AXE_HEAD = new HeadModifier() {
        @Override
        public int modifyMeleeMaxTargets(int value, WeaponData data) {
            return 1;
        }

        @Override
        public float modifyMeleeDamage(float damage, WeaponData data) {
            return super.modifyMeleeDamage(damage, data) + 4;
        }
    };

    //LAUNCHER
    public static final IWeaponModifier BALLISTAZOOKA = new IWeaponModifier() {
        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, WeaponData data) {
            return new LinkedHashSet<>(List.of(FireMode.SEMI_AUTO));
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 1;
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            return 15;
        }

        @Override
        public float modifyProjectileSpread(float spread, WeaponData data) {
            return 1;
        }

//        @Override
//        public double modifyProjectileSpeed(double speed, WeaponData data) {
//            return 40;
//        }

        @Override
        public int modifyReloadTime(int reloadTime, WeaponData data) {
            if(FuelUtils.hasFuel(data)) {
                return 80;
            }
            return 140;
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> secondaryAmmo, WeaponData data) {
            return Set.of(CgsAmmoHolders.AIR);
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, WeaponData data) {
            return Set.of(AmmoHolder.getType(CgsAmmo.SPEAR.getId()));
        }

        @Override
        public ResourceLocation modifyFireSound(ResourceLocation sound, WeaponData data) {
            return CgsSounds.BALLISTA_FIRE.getId();
        }

        @Override
        public boolean modifyAutoReloading(boolean autoReload, WeaponData data) {
            return true;
        }
    };

    public static final IWeaponModifier AUTO_LAUNCHER = new IWeaponModifier() {
        @Override
        public Set<FireMode> modifyFireModes(Set<FireMode> fireMode, WeaponData data) {
            return new LinkedHashSet<>(List.of(FireMode.AUTO));
        }

        @Override
        public int modifyFuelAmountPerUse(ResourceLocation ammo, int value, WeaponData data) {
            if(ammo.equals(CgsAmmoHolders.AIR.getId()))
                return  1;
            return value;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo, WeaponData data) {
            return 30;
        }

        @Override
        public Set<AmmoHolder> modifyAmmoItems(Set<AmmoHolder> item, WeaponData data) {
            return Set.of(AmmoHolder.getType(CgsAmmo.SMALL_ROCKET.getId()));
        }

        @Override
        public int modifyFireRate(int rate, WeaponData data) {
            return 2;
        }

        @Override
        public int modifyReloadTime(int reloadTime, WeaponData data) {
            return 100;
        }

        @Override
        public Set<AmmoHolder> modifyFuelItems(Set<AmmoHolder> secondaryAmmo, WeaponData data) {
            return Set.of(CgsAmmoHolders.AIR);
        }
    };

    public static final IWeaponModifier BIG_BAYONET = new IWeaponModifier() {
//        @Override
//        public boolean modifyCanMelee(boolean value, WeaponData data) {
//            return true;
//        }

        @Override
        public WeaponAction modifyWeaponAction(WeaponAction value, WeaponData data) {
            if(data.weaponMode == WeaponMode.ADDITIONAL){
                return WeaponAction.MELEE;
            }
            return value;
        }

        @Override
        public float modifyMeleeDamage(float value, WeaponData data) {
            return 12;
        }
    };
}