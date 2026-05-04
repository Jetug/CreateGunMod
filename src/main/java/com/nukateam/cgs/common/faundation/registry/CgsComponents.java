package com.nukateam.cgs.common.faundation.registry;

import com.mojang.serialization.Codec;
import com.nukateam.ntgl.Ntgl;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

public class CgsComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Ntgl.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COCK =
            REGISTER.registerComponentType(
                    "cock",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
            );


    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}