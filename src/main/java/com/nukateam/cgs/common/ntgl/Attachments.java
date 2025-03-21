package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.base.GunModifiers;
import com.nukateam.ntgl.common.base.holders.GripType;
import com.nukateam.ntgl.common.base.holders.LoadingType;
import com.nukateam.ntgl.common.data.attachment.impl.Scope;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;

public class Attachments {
    public static final Scope SHORT_SCOPE = Scope.builder()
            .aimFovModifier(0.85F)
            .modifiers(GunModifiers.SLOW_ADS)
            .build();

    public static final Scope LONG_SCOPE = Scope.builder()
            .aimFovModifier(0.25F)
            .modifiers(GunModifiers.SLOWER_ADS)
            .overlay()
            .build();

    public static final IGunModifier STEAM_ENGINE_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyFireRate(int rate) {
            return 2;
        }

        @Override
        public float modifyProjectileSpread(float spread) {
            return spread + 8f;
        }

        @Override
        public GripType modifyGripType(GripType gripType) {
            return IGunModifier.super.modifyGripType(gripType);
        }
    };

    public static final IGunModifier BELT_MODIFIERS = new IGunModifier() {
        @Override
        public int modifyReloadTime(int reloadTime) {
            return 20;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo) {
            return 12;
        }

        @Override
        public LoadingType modifyLoadingType(LoadingType loadingType) {
            return LoadingType.PER_CARTRIDGE;
        }
    };

    public static final IGunModifier AUTO_FIRE = new IGunModifier() {
        @Override
        public int modifyFireDelay(int chargeTime) {
            return 0;
        }
    };

    public static final IGunModifier LONG_BARREL = new IGunModifier() {
        @Override
        public GripType modifyGripType(GripType gripType) {
            return GripType.TWO_HANDED;
        }

        @Override
        public float modifyDamage(float damage) {
            return damage * 1.5f;
        }

        @Override
        public double modifyProjectileSpeed(double speed) {
            return speed * 3;
        }
    };

    public static final IGunModifier AUTO = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 2;
        }
    };


    public static final IGunModifier STOCK = new IGunModifier() {
        @Override
        public float recoilModifier() {
            return 0.1F;
        }

        @Override
        public float kickModifier() {
            return 0.1F;
        }

        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 0.25F;
        }

        @Override
        public double modifyAimDownSightSpeed(double speed) {
            return speed * 0.5F;
        }

    };

    public static final IGunModifier SHOTGUN_DRUM = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo) {
            return 8;
        }

        @Override
        public int modifyReloadTime(int reloadTime) {
            return 80;
        }
    };

    public static final IGunModifier SHOTGUN_PUMP = new IGunModifier() {
        @Override
        public int modifyMaxAmmo(int maxAmmo) {
            return 10;
        }

        @Override
        public GripType modifyGripType(GripType gripType) {
            return GripType.TWO_HANDED;
        }
    };

    public static final IGunModifier SHOTGUN_LONG_BARREL = new IGunModifier() {
        @Override
        public GripType modifyGripType(GripType gripType) {
            return GripType.TWO_HANDED;
        }

        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 0.5f;
        }
    };

    public static final IGunModifier SHOTGUN_SPREAD_BARREL = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread) {
            return spread * 4;
        }
    };

    public static final IGunModifier NAILGUN_SPLIT_BARREL = new IGunModifier() {
        @Override
        public float modifyProjectileSpread(float spread) {
            return 20;
        }

        @Override
        public int modifyMaxAmmo(int maxAmmo) {
            return maxAmmo / 5;
        }

        @Override
        public int modifyProjectileAmount(int amount) {
            return 5;
        }

        @Override
        public int modifyFireRate(int rate) {
            return rate * 3;
        }
    };
}