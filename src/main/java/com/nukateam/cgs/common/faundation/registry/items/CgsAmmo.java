
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CgsAmmo {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> GATLING_SHELL = registerItem("shell_gatling");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> GATLING_ROUND_BLANK = registerAmmo("round_gatling_blank");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> GATLING_ROUND = registerAmmo("round_gatling");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> GATLING_ROUND_PIERCING = registerAmmo("round_gatling_piercing");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> REVOLVER_SHELL = registerItem("shell_revolver");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> REVOLVER_ROUND_BLANK = registerAmmo("round_revolver_blank");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> REVOLVER_ROUND = registerAmmo("round_revolver");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> REVOLVER_ROUND_PIERCING = registerAmmo("round_revolver_piercing");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_SHELL = registerItem("shell_shotgun");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_ROUND_BLANK = registerAmmo("round_shotgun_blank");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_ROUND = registerAmmo("round_shotgun");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_ROUND_INCENDIARY = registerAmmo("round_shotgun_incendiary");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_ROUND_FLECHETTE = registerAmmo("round_shotgun_flechette");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SHOTGUN_ROUND_FLECHETTE_STEEL = registerAmmo("round_shotgun_flechette_steel");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> PAPER_CARTRIDGE = registerAmmo("paper_cartridge");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> PAPER_SHOT = registerAmmo("paper_shot");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> NAIL = registerAmmo("nail");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> STEEL_NAIL = registerAmmo("nail_steel");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> ROCKET = registerAmmo("rocket");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SMALL_ROCKET = registerAmmo("rocket_small");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> SPEAR = registerAmmo("spear");
    @ItemModelGen(path = "ammo")
    public static final RegistryObject<Item> LEAD_BALLS = registerAmmo("lead_balls");
//    @ItemModelGen(path = "ammo")
//    public static final RegistryObject<Item> FLECHETTE = registerAmmo("flechette");
//    @ItemModelGen(path = "ammo")
//    public static final RegistryObject<Item> FLECHETTE_STEEL = registerAmmo("flechette_steel");

//    @ItemModelGen
//    public static final RegistryObject<Item> PAPER_CARTRIDGE_BLANK = registerAmmo("paper_cartridge_blank");

//    @ItemModelGen
//    public static final RegistryObject<Item> NAIL_PIERCING = registerAmmo("nail_piercing");

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
