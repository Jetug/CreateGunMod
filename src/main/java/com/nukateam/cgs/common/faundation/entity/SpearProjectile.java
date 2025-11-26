package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.HitTarget;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import com.nukateam.ntgl.common.util.util.math.ExtendedEntityRayTraceResult;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
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

    @Override
    protected void travel() {
        if (this.hasPassenger((e) -> true)) {
            var motion = this.getDeltaMovement();
            var drag = 0.80;
            this.setDeltaMovement(motion.x * drag, motion.y, motion.z * drag);
        }
        super.travel();
    }

    @Override
    protected void damageEntity(ExtendedEntityRayTraceResult hitResult) {
        var target = hitResult.getEntity();
        if (this.hasPassenger(target)){
            return;
        }

        super.damageEntity(hitResult);
    }

    @Override
    protected void onHitEntity(ExtendedEntityRayTraceResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.getPassengers().isEmpty()) {
            entityHitResult.getEntity().startRiding(this);
        }
    }

    @Override
    protected boolean removeOnHit(HitTarget hitTarget) {
        if(hitTarget == HitTarget.ENTITY){
//            if (this.hasPassenger((e) -> true)){
//                return true;
//            }
//            else
                return false;
        }
        return super.removeOnHit(hitTarget);
    }
}
