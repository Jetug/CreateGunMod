
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.cgs.common.faundation.item.FluidContainerItem;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Function;

public class CgsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    @ItemModelGen
    public static final RegistryObject<Item> EMPTY_CONTAINER = ITEMS.register("tank_empty",
            () -> new FluidContainerItem(() -> Fluids.EMPTY,
                    new Item.Properties().stacksTo(16)));

    @ItemModelGen
    public static final RegistryObject<Item> WATER_CONTAINER = ITEMS.register("tank_water",
            () -> new FluidContainerItem(() -> Fluids.WATER,
                    new Item.Properties().stacksTo(16)));

    @ItemModelGen
    public static final RegistryObject<Item> LAVA_CONTAINER = ITEMS.register("tank_lava",
            () -> new FluidContainerItem(() -> Fluids.LAVA,
                    new Item.Properties().stacksTo(16)));

    public static Map<Fluid, RegistryObject<Item>> CONTAINERS = Map.of(
            Fluids.WATER, WATER_CONTAINER,
            Fluids.LAVA, LAVA_CONTAINER
    );

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_GATLING = registerItem("press_form_gatling");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_REVOLVER = registerItem("press_form_revolver");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_SHOTGUN = registerItem("press_form_shotgun");

    @ItemModelGen
    public static final RegistryObject<Item> LEAD_INGOT = registerItem ("lead_ingot");

    @ItemModelGen
    public static final RegistryObject<Item> STEEL_INGOT = registerItem ("steel_ingot");

    @ItemModelGen
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget");

    @ItemModelGen
    public static final RegistryObject<Item> STEEL_NUGGET = registerItem ("steel_nugget");

    @ItemModelGen
    public static final RegistryObject<Item> STEEL_SHEET = registerItem ("steel_sheet");

    @ItemModelGen
    public static final RegistryObject<Item> RAW_LEAD = registerItem("raw_lead");

    @ItemModelGen
    public static final RegistryObject<Item> NITER = registerItem ("niter");

    @ItemModelGen
    public static final RegistryObject<Item> SULFUR = registerItem ("sulfur");

    @ItemModelGen
    public static final RegistryObject<Item> CHARCOAL_DUST = registerItem ("charcoal_dust");

    @ItemModelGen
    public static final RegistryObject<Item> GUANO = ITEMS.register("guano",
            () -> new ItemNameBlockItem(CgsBlocks.GUANO_BLOCK.get(), new Item.Properties()));


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
