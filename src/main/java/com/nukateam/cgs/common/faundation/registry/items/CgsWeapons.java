
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.guns.*;
import com.nukateam.cgs.common.ntgl.modifiers.HammerModifier;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;
import java.util.function.Function;

public class CgsWeapons {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEMS, Gunsmithing.MOD_ID);
    //GUNS
    public static final DeferredHolder<WeaponItem> FLINTLOCK = registerGun("flintlock", FlintlockItem::new);
    public static final DeferredHolder<WeaponItem> REVOLVER = registerGun("revolver", RevolverItem::new);
    public static final DeferredHolder<WeaponItem> SHOTGUN = registerGun("shotgun", ShotgunItem::new);
    public static final DeferredHolder<WeaponItem> NAILGUN = registerGun("nailgun", NailgunItem::new);
    public static final DeferredHolder<WeaponItem> GATLING = registerGun("gatling", GatlingItem::new);
    public static final DeferredHolder<WeaponItem> BLAZEGUN = registerGun("blazegun", BlazegunItem::new);
    public static final DeferredHolder<WeaponItem> LAUNCHER = registerGun("launcher", LauncherItem::new);
    public static final DeferredHolder<WeaponItem> HAMMER = registerGun("hammer", properties ->
            new HammerItem(properties,  new HammerModifier()));

    //GRENADES
    public static final DeferredHolder<WeaponItem> GRENADE = registerGrenade("frag_grenade", WeaponItem::new);

    private static DeferredHolder<WeaponItem> registerGun(String name, Function<Item.Properties, WeaponItem> item) {
        return ITEMS.register(name,
                () -> item.apply(new Item.Properties().stacksTo(1)));
    }

    private static DeferredHolder<WeaponItem> registerGrenade(String name, Function<Item.Properties, WeaponItem> item) {
        return ITEMS.register(name, () -> item.apply(new Item.Properties().stacksTo(16)));
    }

    public static DeferredHolder<WeaponItem> registerGun(String name) {
        return ITEMS.register(name, () -> new CgsGunItem(new Item.Properties().stacksTo(1)));
    }

    public static DeferredHolder<WeaponItem> registerGun(String name, int durability) {
        return ITEMS.register(name, () -> new CgsGunItem(new Item.Properties().durability(durability)));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}