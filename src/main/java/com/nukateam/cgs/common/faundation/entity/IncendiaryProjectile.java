package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import static software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache;

public class IncendiaryProjectile extends ProjectileEntity {
    public IncendiaryProjectile(EntityType<? extends ProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IncendiaryProjectile(EntityType<? extends ProjectileEntity> entityType, Level level, WeaponData data) {
        super(entityType, level, data);
    }

    @Override
    protected void onProjectileTick() {
        if (this.level().isClientSide && !isUnderWater()) {
            for (int i = 5; i > 0; i--) {
                this.level().addParticle(ParticleTypes.FLAME, true, this.getX() - (this.getDeltaMovement().x() / i), this.getY() - (this.getDeltaMovement().y() / i), this.getZ() - (this.getDeltaMovement().z() / i), 0, 0, 0);
            }
        }
    }
}
