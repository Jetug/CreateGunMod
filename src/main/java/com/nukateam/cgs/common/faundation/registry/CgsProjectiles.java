package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.entity.*;
import com.nukateam.chassis_core.modules.example.common.entities.ExampleChassis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

import java.util.function.BiFunction;

public class CgsProjectiles {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, Gunsmithing.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<BlazeProjectile>> FIREBALL =
            registerProjectile("fireball", BlazeProjectile::new);

    public static final DeferredHolder<EntityType<?>, EntityType<RocketProjectile>> ROCKET =
            registerProjectile("rocket", RocketProjectile::new);

    public static final DeferredHolder<EntityType<?>, EntityType<RocketProjectile>> SMALL_ROCKET =
            registerProjectile("small_rocket", RocketProjectile::new);

    public static final DeferredHolder<EntityType<?>, EntityType<NailProjectile>> NAIL =
            registerProjectile("nail", NailProjectile::new);

    public static final DeferredHolder<EntityType<?>, EntityType<SpearProjectile>> SPEAR =
            registerProjectile("spear", SpearProjectile::new);

    public static final DeferredHolder<EntityType<?>, EntityType<IncendiaryProjectile>> INCENDIARY =
            registerProjectile("incendiary", IncendiaryProjectile::new);

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerProjectile(
            String id, BiFunction<EntityType<T>, Level, T> function) {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
                .setTrackingRange(100)
                .setUpdateInterval(1)
                .noSummon()
                .fireImmune()
                .setShouldReceiveVelocityUpdates(true).build(id));
    }
    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerFireball(
            String id, BiFunction<EntityType<T>, Level, T> function) {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(4F, 4F)
                .setTrackingRange(100)
                .setUpdateInterval(1)
                .noSummon()
                .fireImmune()
                .setShouldReceiveVelocityUpdates(true).build(id));
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
