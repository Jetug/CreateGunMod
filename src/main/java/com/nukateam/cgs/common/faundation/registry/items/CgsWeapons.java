
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.guns.*;
import com.nukateam.cgs.common.ntgl.modifiers.HammerModifier;
import com.nukateam.ntgl.common.foundation.item.ThrowableItem;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import java.util.function.Function;

public class CgsWeapons {
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
    public static final RegistryObject<WeaponItem> LAUNCHER = registerGun("launcher", LauncherItem::new);
    public static final RegistryObject<WeaponItem> HAMMER = registerGun("hammer", properties ->
            new HammerItem(properties,  new HammerModifier()));

    public static final RegistryObject<ThrowableItem> GRENADE = registerGrenade("frag_grenade", ThrowableItem::new);

    private static RegistryObject<WeaponItem> registerGun(String name, Function<Item.Properties, WeaponItem> item) {
        return ITEMS.register(name,
                () -> item.apply(new Item.Properties().stacksTo(1)));
    }

    private static RegistryObject<ThrowableItem> registerGrenade(String name, Function<Item.Properties, ThrowableItem> item) {
        return ITEMS.register(name, () -> item.apply(new Item.Properties().stacksTo(16)));
    }

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