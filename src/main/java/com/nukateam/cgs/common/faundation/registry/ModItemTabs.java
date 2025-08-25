package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.items.ModItems;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.ntgl.common.util.helpers.RegistrationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.CreativeModeTab.*;

public class ModItemTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gunsmithing.MOD_ID);


    public static final RegistryObject<CreativeModeTab> GUNS = CREATIVE_MODE_TABS.register("mod_items",
            () -> builder().icon(() -> new ItemStack(ModItems.ROUND_7MM.get()))
                    .title(Component.translatable("itemGroup.mod_items"))
                    .displayItems(ModItemTabs::getWeaponTab)
                    .build());



    private static void getWeaponTab(ItemDisplayParameters itemDisplayParameters, Output output) {
        registerGuns(output, CgsWeapons.ITEMS);
        registerItems(output, ModItems.ITEMS);
        registerItems(output, AttachmentItems.ITEMS);

    }

    private static void registerItems(Output output, DeferredRegister<Item> register) {
        for (var entry : register.getEntries()) {
            output.accept(entry.get());
        }
    }

    private static void registerGuns(Output output, DeferredRegister<Item> register) {
        for (var entry : register.getEntries()) {
            RegistrationHelper.registerGunOrDefault(output, entry.get());
        }
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
