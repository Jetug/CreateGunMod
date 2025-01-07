package com.nukateam.gunsmithing.common.faundation.registry;

import com.nukateam.gunsmithing.Gunsmithing;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.CreativeModeTab.*;

public class ModItemTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gunsmithing.MOD_ID);


    public static final RegistryObject<CreativeModeTab> GUNS = CREATIVE_MODE_TABS.register("nuka_equip",
            () -> builder().icon(() -> new ItemStack(ModGuns.ROUND7MM.get()))
                    .title(Component.translatable("itemGroup.nuka_equip"))
                    .displayItems(ModItemTabs::getWeaponTab)
                    .build());



    private static void getWeaponTab(ItemDisplayParameters itemDisplayParameters, Output output) {
        registerItems(output, ModGuns.ITEMS);
        registerItems(output, ModItems.ITEMS);
    }

    private static void registerItems(Output output, DeferredRegister<Item> register) {
        for (var entry : register.getEntries()) {
            output.accept(entry.get());
        }
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
