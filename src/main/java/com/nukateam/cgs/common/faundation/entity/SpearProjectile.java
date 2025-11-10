package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.core.particles.ParticleTypes;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import static software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache;

public class SpearProjectile extends ProjectileEntity implements AnimatedProjectile {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);

    public SpearProjectile(EntityType<? extends ProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SpearProjectile(EntityType<? extends ProjectileEntity> entityType, Level level, WeaponData data) {
        super(entityType, level, data);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
