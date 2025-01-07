package com.nukateam.gunsmithing.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "defineSynchedData", at = @At(value = "TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
//        entityData.define(PlayerData.IS_TURNED, false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag pCompound, CallbackInfo ci) {
//        var data = new PlayerData((Player)(LivingEntity) this);
//        pCompound.putBoolean("IsTurned", data.isTurned());
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag pCompound, CallbackInfo ci) {
//        this.getEntityData().set(PlayerData.IS_TURNED, pCompound.getBoolean("IsTurned"));
    }
}
