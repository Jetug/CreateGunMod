package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.common.faundation.entity.BlazeProjectile;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.entity.FlameProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class ModProjectiles {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ntgl.MOD_ID);

    public static final RegistryObject<EntityType<BlazeProjectile>> FIREBALL =
            registerProjectile("fireball", BlazeProjectile::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerProjectile(
            String id, BiFunction<EntityType<T>, Level, T> function) {
        return REGISTER.register(id, () -> EntityType.Builder.of(function::apply, MobCategory.MISC)
                .sized(0.25F, 0.25F)
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
