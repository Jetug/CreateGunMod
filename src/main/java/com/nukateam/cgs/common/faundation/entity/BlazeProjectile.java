package com.nukateam.cgs.common.faundation.entity;

import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

import static net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent;

public class BlazeProjectile extends ProjectileEntity implements ItemSupplier {
    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon, WeaponItem item, Gun modifiedGun) {
        super(entityType, worldIn, shooter, weapon, item, modifiedGun);
    }

    protected Predicate<BlockState> getBlockFilter() {
        return (value) -> false;
    }

//    protected ItemStack getItemRaw() {
//        return this.getEntityData().get(DATA_ITEM_STACK);
//    }

    @Override
    public ItemStack getItem() {
//        ItemStack itemstack = this.getItemRaw();
//        return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
        return new ItemStack(Items.FIRE_CHARGE);
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
}
