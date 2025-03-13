
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.*;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import java.util.function.Function;

public class ModGuns {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

//    public static final RegistryObject<GunItem> MACHINE_GUN = registerGun("machine_gun");
//    public static final RegistryObject<GunItem> FLAMER = registerGun("flamethrower");

    ///GUNS
    public static final RegistryObject<GunItem> REVOLVER = registerGun("revolver", RevolverItem::new);
    public static final RegistryObject<GunItem> GATLING = registerGun("gatling", GatlingItem::new);
    public static final RegistryObject<GunItem> FLINTLOCK = registerGun("flintlock", FlintlockItem::new);
    public static final RegistryObject<GunItem> NAILGUN = registerGun("nailgun", BaseGunItem::new);
    public static final RegistryObject<GunItem> SHOTGUN = registerGun("shotgun", ShotgunItem::new);

    private static RegistryObject<GunItem> registerGun(String name, Function<Item.Properties, GunItem> item) {
        return ITEMS.register(name,
                () -> item.apply(new Item.Properties().stacksTo(1)));
    }

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