
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class CgsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    @ItemModelGen
    public static final RegistryObject<Item> RAW_LEAD = registerItem   ("raw_lead");
    
    @ItemModelGen
    public static final RegistryObject<Item> LEAD_INGOT = registerItem ("lead_ingot");

    @ItemModelGen
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget");

    @ItemModelGen
    public static final RegistryObject<Item> STEEL_INGOT = registerItem ("steel_ingot");

    @ItemModelGen
    public static final RegistryObject<Item> STEEL_SHEET = registerItem ("steel_sheet");

    @ItemModelGen
    public static final RegistryObject<Item> CHARCOAL_DUST = registerItem ("charcoal_dust");

    @ItemModelGen
    public static final RegistryObject<Item> SULFUR = registerItem ("sulfur");

    @ItemModelGen
    public static final RegistryObject<Item> GUANO = registerItem ("guano");

    @ItemModelGen
    public static final RegistryObject<Item> SELITRA = registerItem ("selitra");

    @ItemModelGen
    public static final RegistryObject<Item> BARREL = registerItem("barrel");

    @ItemModelGen
    public static final RegistryObject<Item> STURDY_BARREL = registerItem("barrel_sturdy");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_GATLING = registerItem("press_form_gatling");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_REVOLVER = registerItem("press_form_revolver");

    @ItemModelGen
    public static final RegistryObject<Item> SHOTGUN_PRESS_FORM = registerItem("press_form_shotgun");


    private static RegistryObject<Item> registerAmmo(String name, Function<Item.Properties, AmmoItem> item) {
        return ITEMS.register(name, () -> item.apply(new Item.Properties()));
    }

    public static RegistryObject<Item> registerAmmo(String name) {
        return ITEMS.register(name, () -> new AmmoItem(new Item.Properties()));
    }

    public static RegistryObject<Item> registerItem(String name, Item.Properties properties) {
        return ITEMS.register(name, () -> new Item(properties));
    }

    public static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, new Item.Properties());
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
