package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.MissileEntity;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import static software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache;

public class RocketProjectile extends MissileEntity implements AnimatedProjectile {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);

    public RocketProjectile(EntityType<? extends ProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RocketProjectile(EntityType<? extends ProjectileEntity> entityType, Level level, WeaponData data) {
        super(entityType, level, data);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
