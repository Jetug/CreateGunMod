package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static mod.azure.azurelib.util.AzureLibUtil.createInstanceCache;

public class GeoProjectile extends ProjectileEntity implements AnimatedProjectile {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);

    public GeoProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public GeoProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, WeaponItem item, Gun modifiedGun) {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
