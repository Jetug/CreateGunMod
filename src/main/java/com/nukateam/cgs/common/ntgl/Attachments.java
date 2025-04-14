package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.ntgl.common.base.GunModifiers;
import com.nukateam.ntgl.common.base.holders.*;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.util.util.GunData;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.nukateam.cgs.client.ShotgunAnimator.isAmmoEven;

public class Attachments {
    public static final int MAX_FUEL = 20000;
    public static final int MAX_WATER = 2000;
    public static final int MAX_AIR = 900;

    public static final Scope SHORT_SCOPE = Scope.builder()
            .aimFovModifier(0.85F)
            .modifiers(GunModifiers.SLOW_ADS)
            .build();

    public static final Scope LONG_SCOPE = Scope.builder()
            .modifiers(GunModifiers.SLOWER_ADS)
            .aimFovModifier(0.25F)
            .overlay()
            .build();

    public static final IGunModifier STEAM_ENGINE_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyFireRate(int rate, GunData data) {
            return 2;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread + 8f;
        }

        @Override
        public Set<FuelType> modifyFuel(Set<FuelType> secondaryAmmo, GunData data) {
            return Set.of(FuelType.BURNABLE, FuelType.WATER);
        }

        @Override
        public int modifyMaxFuel(int max, FuelType type, GunData data) {
            if(type == FuelType.BURNABLE)
                return MAX_FUEL;
            else if(type == FuelType.WATER)
                return MAX_WATER;
            return max;
        }

        @Override
        public GripType modifyGripType(GripType gripType, GunData data) {
            if(data == null) return IGunModifier.super.modifyGripType(gripType, data);
            var hasDrum = Gun
                    .getAttachmentItem(AttachmentType.MAGAZINE, data.gun)
                    .getItem() == AttachmentItems.GATLING_DRUM.get();

            if(!hasDrum && data.shooter.hasEffect(MobEffects.DAMAGE_BOOST)){
                return GripType.ONE_HANDED;
            }
            return IGunModifier.super.modifyGripType(gripType, data);
        }
    };

    public static final IGunModifier GATLING_DRUM_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo, GunData data) {
            return 300;
        }

        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 2;
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
            return Set.of(FireMode.AUTO, FireMode.SEMI_AUTO);
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

    public static final IGunModifier AUTO = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 2;
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
            if(!isAmmoEven(data.gun))
                return 20;
            return IGunModifier.super.modifyFireRate(rate, data);
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
        public float modifyProjectileSpread(float spread, GunData data) {
            return spread * 4;
        }
    };

    public static final IGunModifier NAILGUN_SPLIT_BARREL = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread, GunData data) {
            return 20;
        }

        @Override
        public int modifyProjectileAmount(int amount, GunData data) {
            return Math.min(5, Gun.getAmmo(data.gun));
        }

        @Override
        public int modifyAmmoPerShot(int ammoPerShot, GunData data) {
            return 5;
        }

        @Override
        public int modifyFireRate(int rate, GunData data) {
            return rate * 3;
        }
    };
}