
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.guns.*;
import com.nukateam.ntgl.common.foundation.item.ThrowableItem;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import java.util.function.Function;

public class ModGuns {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

//    public static final RegistryObject<WeaponItem> MACHINE_GUN = registerGun("machine_gun");
//    public static final RegistryObject<WeaponItem> FLAMER = registerGun("flamethrower");

    //GUNS
    public static final RegistryObject<WeaponItem> FLINTLOCK = registerGun("flintlock", FlintlockItem::new);
    public static final RegistryObject<WeaponItem> REVOLVER = registerGun("revolver", RevolverItem::new);
    public static final RegistryObject<WeaponItem> SHOTGUN = registerGun("shotgun", ShotgunItem::new);
    public static final RegistryObject<WeaponItem> NAILGUN = registerGun("nailgun", NailgunItem::new);
    public static final RegistryObject<WeaponItem> GATLING = registerGun("gatling", GatlingItem::new);
    public static final RegistryObject<WeaponItem> FLAMETHROWER = registerGun("flamethrower", FlamethrowerItem::new);
    public static final RegistryObject<WeaponItem> ROCKET_LAUNCHER = registerGun("rocket_launcher", RocketLauncherItem::new);
    public static final RegistryObject<WeaponItem> HAMMER = registerGun("hammer", HammerItem::new);

    public static final RegistryObject<ThrowableItem> GRENADE = ITEMS.register("frag_grenade",
            () -> new ThrowableItem(new Item.Properties().stacksTo(1)));

    private static RegistryObject<WeaponItem> registerGun(String name, Function<Item.Properties, WeaponItem> item) {
        return ITEMS.register(name,
                () -> item.apply(new Item.Properties().stacksTo(1)));
    }

//    private static RegistryObject<WeaponItem> registerGrenade(String name, Function<Item.Properties, GrenadeItem> item) {
//        return ITEMS.register(name,
//                () -> item.apply(new Item.Properties().stacksTo(1)));
//    }

    public static RegistryObject<WeaponItem> registerGun(String name) {
        return ITEMS.register(name, () -> new CgsGunItem(new Item.Properties().stacksTo(1)));
    }

    public static RegistryObject<WeaponItem> registerGun(String name, int durability) {
        return ITEMS.register(name, () -> new CgsGunItem(new Item.Properties().durability(durability)));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}