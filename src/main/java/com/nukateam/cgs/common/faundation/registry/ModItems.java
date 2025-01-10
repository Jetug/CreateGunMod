
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.data.DataGen;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    @DataGen
    public static final RegistryObject<Item> BARREL = ITEMS.register("barrel", () -> new Item(new Item.Properties()));

    @DataGen
    public static final RegistryObject<Item> STURDY_BARREL = ITEMS.register("barrel_sturdy", () -> new Item(new Item.Properties()));

    @DataGen
    public static final RegistryObject<Item> PRESS_FORM_7MM = ITEMS.register("press_form_7mm", () -> new Item(new Item.Properties()));

    @DataGen
    public static final RegistryObject<Item> SHELL_7MM = ITEMS.register("shell_7mm", () -> new Item(new Item.Properties()));

    @DataGen
    public static final RegistryObject<Item> ROUND_7MM = registerAmmo("round_7mm");

    public static RegistryObject<Item> registerAmmo(String name) {
        return ITEMS.register(name, () -> new AmmoItem(new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
