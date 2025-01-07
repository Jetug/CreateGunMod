
package com.nukateam.gunsmithing.common.faundation.registry;

import com.nukateam.gunsmithing.Gunsmithing;
import com.nukateam.gunsmithing.common.data.DataGen;
import com.nukateam.gunsmithing.common.faundation.item.BaseGunItem;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.GunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class ModGuns {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    ///GUNS
    public static final RegistryObject<GunItem> REVOLVER = registerGun("revolver");
    public static final RegistryObject<GunItem> PISTOL10MM = registerGun("machine_gun");
    public static final RegistryObject<GunItem> GATLING = registerGun("gatling");
    public static final RegistryObject<GunItem> CLASSIC10MM = registerGun("flamethrower");

    @DataGen
    public static final RegistryObject<Item> ROUND7MM = registerAmmo("round7mm");

    public static RegistryObject<GunItem> registerGun(String name) {
        return ITEMS.register(name, () -> new BaseGunItem(new Item.Properties().stacksTo(1)));
    }

    public static RegistryObject<GunItem> registerGun(String name, int durability) {
        return ITEMS.register(name, () -> new BaseGunItem(new Item.Properties().durability(durability)));
    }

    public static RegistryObject<Item> registerAmmo(String name) {
        return ITEMS.register(name, () -> new AmmoItem(new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
