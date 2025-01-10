
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.data.DataGen;
import com.nukateam.cgs.common.faundation.item.BaseGunItem;
import com.nukateam.cgs.common.faundation.item.GatlingItem;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class ModGuns {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    ///GUNS
    public static final RegistryObject<GunItem> REVOLVER = registerGun("revolver");
    public static final RegistryObject<GunItem> MACHINE_GUN = registerGun("machine_gun");
    public static final RegistryObject<GunItem> FLAMER = registerGun("flamethrower");

    public static final RegistryObject<GunItem> GATLING = ITEMS.register("gatling",
            () -> new GatlingItem(new Item.Properties().stacksTo(1)));



    public static RegistryObject<GunItem> registerGun(String name) {
        return ITEMS.register(name, () -> new BaseGunItem(new Item.Properties().stacksTo(1)));
    }

    public static RegistryObject<GunItem> registerGun(String name, int durability) {
        return ITEMS.register(name, () -> new BaseGunItem(new Item.Properties().durability(durability)));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}