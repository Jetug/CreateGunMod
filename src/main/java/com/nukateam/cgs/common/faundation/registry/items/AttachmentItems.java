package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.Attachments;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.attachment.impl.Barrel;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.attachment.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.nukateam.cgs.common.ntgl.Attachments.*;
import static com.nukateam.cgs.common.ntgl.CgsAttachmentTypes.ENGINE;

public class AttachmentItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().stacksTo(1);

    //GENERIC
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SCOPE = ITEMS.register("scope",
            () -> new ScopeItem(LONG_SCOPE, ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> STOCK = ITEMS.register("stock",
            () -> new AttachmentItem<>(AttachmentType.STOCK, GenericAttachment.create(Attachments.STOCK), ITEM_PROPERTIES));

    //FLINTLOCK
//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> FLINTLOCK_LONG_BARREL = ITEMS.register("flintlock_long_barrel",
            () -> new BarrelItem(Barrel.create(11f, Attachments.LONG_BARREL), ITEM_PROPERTIES));


    public static final RegistryObject<Item> FLINTLOCK_BAYONET = ITEMS.register("flintlock_bayonet",
            () -> new AttachmentItem<>(AttachmentType.MELEE,
                    GenericAttachment.create(Attachments.BAYONET_MODIFIERS), ITEM_PROPERTIES));

    //REVOLVER
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> REVOLVER_LONG_BARREL = ITEMS.register("long_barrel",
            () -> new BarrelItem(Barrel.create(11f, Attachments.LONG_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> REVOLVER_BELT = ITEMS.register("round_belt",
            () -> new AttachmentItem<>(CgsAttachmentTypes.CHAMBER,
                    GenericAttachment.create(BELT_MODIFIERS), ITEM_PROPERTIES));
 
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> REVOLVER_AUTO = ITEMS.register("auto_fire",
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
                    GenericAttachment.create(AUTO_FIRE), ITEM_PROPERTIES));

    //SHOTGUN
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SHOTGUN_DRUM = ITEMS.register("shotgun_drum",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_DRUM_MODIFIER, SHOTGUN_MODIFIER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SHOTGUN_PUMP = ITEMS.register("shotgun_pump",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_PUMP_MODIFIER, SHOTGUN_MODIFIER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SHOTGUN_LONG_BARREL = ITEMS.register("shotgun_long_barrel",
            () -> new BarrelItem(Barrel.create(11f, Attachments.SHOTGUN_LONG_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SHOTGUN_SPREAD_BARREL = ITEMS.register("shotgun_spread_barrel",
            () -> new BarrelItem(Barrel.create(1f, Attachments.SHOTGUN_SPREAD_BARREL), ITEM_PROPERTIES));

    //GATLING
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> STEAM_ENGINE = ITEMS.register("steam_engine",
            () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> IRON_SIGHT = ITEMS.register("iron_sight",
            () -> new ScopeItem(SHORT_SCOPE, ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> GATLING_DRUM = ITEMS.register("gatling_drum",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(GATLING_DRUM_MODIFIERS), ITEM_PROPERTIES));

    //NAILGUN
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> NAILGUN_SPLITTER = ITEMS.register("nailgun_splitter",
            () -> new BarrelItem(Barrel.create(1f, Attachments.NAILGUN_SPLIT_BARREL), ITEM_PROPERTIES));

    //FLAMETHROWER
//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> POTION_TANK = ITEMS.register("potion_tank",
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
                    GenericAttachment.create(), ITEM_PROPERTIES));

    //HAMMER
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> HAMMER_STONE = ITEMS.register("hammer_stone",
            () -> new HammerHeadItem(Tiers.STONE, HammerHeadItem.Type.HAMMER, GenericAttachment.create(), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> HAMMER_IRON = ITEMS.register("hammer_iron",
            () -> new HammerHeadItem(Tiers.IRON, HammerHeadItem.Type.HAMMER, GenericAttachment.create(), new Item.Properties()));

    public static final RegistryObject<Item> HAMMER_DIAMOND = ITEMS.register("hammer_diamond",
            () -> new HammerHeadItem(Tiers.DIAMOND, HammerHeadItem.Type.HAMMER, GenericAttachment.create(), new Item.Properties()));

    public static final RegistryObject<Item> HAMMER_NETHERITE = ITEMS.register("hammer_netherite",
            () -> new HammerHeadItem(Tiers.NETHERITE, HammerHeadItem.Type.HAMMER, GenericAttachment.create(), new Item.Properties()));

    //
    public static final RegistryObject<Item> AXE_STONE = ITEMS.register("axe_stone",
            () -> new HammerHeadItem(Tiers.STONE, HammerHeadItem.Type.AXE, GenericAttachment.create(), new Item.Properties()));

    public static final RegistryObject<Item> AXE_IRON = ITEMS.register("axe_iron",
            () -> new HammerHeadItem(Tiers.IRON, HammerHeadItem.Type.AXE, GenericAttachment.create(), new Item.Properties()));

    public static final RegistryObject<Item> AXE_DIAMOND = ITEMS.register("axe_diamond",
            () -> new HammerHeadItem(Tiers.DIAMOND, HammerHeadItem.Type.AXE, GenericAttachment.create(), new Item.Properties()));

    public static final RegistryObject<Item> AXE_NETHERITE = ITEMS.register("axe_netherite",
            () -> new HammerHeadItem(Tiers.NETHERITE, HammerHeadItem.Type.AXE, GenericAttachment.create(), new Item.Properties()));


    public static final RegistryObject<Item> SHOTGUN_RECEIVER = ITEMS.register("shotgun_receiver",
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME, GenericAttachment.create(), ITEM_PROPERTIES));

    //LAUNCHER
//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> ROCKET_CONTAINER = ITEMS.register("rocket_container",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> AUTO_LAUNCHER = ITEMS.register("auto_launcher",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> BALLISTAZOOKA = ITEMS.register("ballistazooka",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> GRAPPLING_HOOK = ITEMS.register("grappling_hook",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> ITEM_LAUNCHER = ITEMS.register("item_launcher",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> LAUNCHER_BAYONET = ITEMS.register("launcher_bayonet",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(), ITEM_PROPERTIES));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
