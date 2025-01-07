
package com.nukateam.gunsmithing.common.faundation.registry;

import com.nukateam.gunsmithing.Gunsmithing;
import com.nukateam.gunsmithing.common.data.DataGen;
import com.nukateam.gunsmithing.common.data.ItemParent;
import com.nukateam.gunsmithing.common.faundation.item.BaseGunItem;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
