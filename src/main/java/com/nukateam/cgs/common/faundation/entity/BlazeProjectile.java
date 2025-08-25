package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.entity.FlameProjectile;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

import static mod.azure.azurelib.util.AzureLibUtil.createInstanceCache;
import static net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent;

public class BlazeProjectile extends ProjectileEntity implements ItemSupplier, AnimatedProjectile {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);

    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, WeaponItem item, Gun modifiedGun) {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    protected Predicate<BlockState> getBlockFilter() {
        return (value) -> false;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.FIRE_CHARGE);
    }

    @Override
    protected void onProjectileTick() {
        if (this.level().isClientSide) {
            for (int i = 5; i > 0; i--) {
                this.level().addParticle(ParticleTypes.FLAME, true, this.getX() - (this.getDeltaMovement().x() / i), this.getY() - (this.getDeltaMovement().y() / i), this.getZ() - (this.getDeltaMovement().z() / i), 0, 0, 0);
            }
            if (this.level().random.nextInt(2) == 0) {
                this.level().addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                this.level().addParticle(ParticleTypes.FLAME, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(Entity entity, Vec3 hitVec, Vec3 startVec, Vec3 endVec, boolean headshot) {
        super.onHitEntity(entity, hitVec, startVec, endVec, headshot);
        if (!this.level().isClientSide) {
            var owner = this.getShooter();
            entity.setSecondsOnFire(5);
            this.doEnchantDamageEffects(owner, entity);
        }
    }

    @Override
    protected void onHitBlock(BlockState blockstate, BlockPos blockpos, Direction face, double x, double y, double z) {
        super.onHitBlock(blockstate, blockpos, face, x, y, z);

        if (!this.level().isClientSide) {
            Entity entity = this.getShooter();
            if (!(entity instanceof Mob) || getMobGriefingEvent(this.level(), entity)) {
                blockpos = blockpos.relative(face);
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
