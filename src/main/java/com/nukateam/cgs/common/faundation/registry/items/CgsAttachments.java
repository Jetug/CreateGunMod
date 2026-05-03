package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.AttachmentMods;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.attachment.impl.Barrel;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.attachment.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.nukateam.cgs.common.ntgl.AttachmentMods.*;
import static com.nukateam.cgs.common.ntgl.CgsAttachmentTypes.ENGINE;

public class CgsAttachments {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Gunsmithing.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().stacksTo(1);

    //GENERIC
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> SCOPE = ITEMS.register("scope",
            () -> new ScopeItem(LONG_SCOPE, ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> STOCK = ITEMS.register("stock",
            () -> new AttachmentItem<>(AttachmentType.STOCK, GenericAttachment.create(AttachmentMods.STOCK), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> BAYONET = ITEMS.register("bayonet",
            () -> new AttachmentItem<>(AttachmentType.MUZZLE,
                    GenericAttachment.create(AttachmentMods.BAYONET_MODIFIERS), ITEM_PROPERTIES));

    //FLINTLOCK
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> FLINTLOCK_LONG_BARREL = ITEMS.register("flintlock_long_barrel",
            () -> new BarrelItem(Barrel.create(11f, AttachmentMods.LONG_BARREL), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> FLINTLOCK_MORTAR_BARREL = ITEMS.register("flintlock_mortar_barrel",
//            () -> new BarrelItem(Barrel.create(5f, AttachmentMods.MORTAR_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> BLUNDERBUSS_BARREL = ITEMS.register("blunderbuss_barrel",
            () -> new BarrelItem(Barrel.create(5f, AttachmentMods.BLUNDERBUSS_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> LONG_BLUNDERBUSS_BARREL = ITEMS.register("blunderbuss_barrel_long",
            () -> new BarrelItem(Barrel.create(10f, AttachmentMods.LONG_BLUNDERBUSS_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> REVOLVING_CHAMBERS = ITEMS.register("flintlock_chambers",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(AttachmentMods.REVOLVING_CHAMBERS), ITEM_PROPERTIES));

    //REVOLVER
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> REVOLVER_LONG_BARREL = ITEMS.register("long_barrel",
            () -> new BarrelItem(Barrel.create(11f, AttachmentMods.LONG_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> REVOLVER_BELT = ITEMS.register("round_belt",
            () -> new AttachmentItem<>(CgsAttachmentTypes.CHAMBER,
                    GenericAttachment.create(BELT_MODIFIERS), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> REVOLVER_AUTO = ITEMS.register("auto_fire",
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
                    GenericAttachment.create(AUTO_FIRE), ITEM_PROPERTIES));

    //SHOTGUN
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> SHOTGUN_DRUM = ITEMS.register("shotgun_drum",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_DRUM_MODIFIER, SHOTGUN_MODIFIER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> SHOTGUN_PUMP = ITEMS.register("shotgun_pump",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_PUMP_MODIFIER, SHOTGUN_MODIFIER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> SHOTGUN_LONG_BARREL = ITEMS.register("shotgun_long_barrel",
            () -> new BarrelItem(Barrel.create(11f, AttachmentMods.SHOTGUN_LONG_BARREL), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> SHOTGUN_SPREAD_BARREL = ITEMS.register("shotgun_spread_barrel",
            () -> new BarrelItem(Barrel.create(1f, AttachmentMods.SHOTGUN_SPREAD_BARREL), ITEM_PROPERTIES));

    //GATLING
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> STEAM_ENGINE = ITEMS.register("steam_engine",
            () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> IRON_SIGHT = ITEMS.register("iron_sight",
//            () -> new ScopeItem(SHORT_SCOPE, ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> GATLING_DRUM = ITEMS.register("gatling_drum",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(GATLING_DRUM_MODIFIERS), ITEM_PROPERTIES));

    //NAILGUN
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> NAILGUN_SPLITTER = ITEMS.register("nailgun_splitter",
            () -> new BarrelItem(Barrel.create(1f, AttachmentMods.NAILGUN_SPLIT_BARREL), ITEM_PROPERTIES));

//    //BLAZEGUN
//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> POTION_TANK = ITEMS.register("potion_tank",
//            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
//                    GenericAttachment.create(), ITEM_PROPERTIES));

    //LAUNCHER
//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> ROCKET_CONTAINER = ITEMS.register("rocket_container",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> AUTO_LAUNCHER = ITEMS.register("launcher_auto",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(AttachmentMods.AUTO_LAUNCHER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> BALLISTAZOOKA = ITEMS.register("ballistazooka",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(AttachmentMods.BALLISTAZOOKA), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> HOOK_LAUNCHER = ITEMS.register("launcher_hook",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), ITEM_PROPERTIES));

//    @ItemModelGen(path = "attachments")
//    public static final DeferredHolder<Item, Item> ITEM_LAUNCHER = ITEMS.register("item_launcher",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> LAUNCHER_BAYONET = ITEMS.register("big_bayonet",
            () -> new AttachmentItem<>(AttachmentType.MUZZLE,
                    GenericAttachment.create(AttachmentMods.BIG_BAYONET), ITEM_PROPERTIES));

    //HAMMER
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> HAMMER_CHAMBER = ITEMS.register("hammer_chamber",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(RECIEVER), ITEM_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> HAMMER_STONE = ITEMS.register("hammer_stone",
            () -> new HammerHeadItem(Tiers.STONE, HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> HAMMER_IRON = ITEMS.register("hammer_iron",
            () -> new HammerHeadItem(Tiers.IRON, HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> HAMMER_DIAMOND = ITEMS.register("hammer_diamond",
            () -> new HammerHeadItem(Tiers.DIAMOND, HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> HAMMER_NETHERITE = ITEMS.register("hammer_netherite",
            () -> new HammerHeadItem(Tiers.NETHERITE, HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), new Item.Properties()));
    //
    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> AXE_STONE = ITEMS.register("axe_stone",
            () -> new HammerHeadItem(Tiers.STONE, HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> AXE_IRON = ITEMS.register("axe_iron",
            () -> new HammerHeadItem(Tiers.IRON, HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> AXE_DIAMOND = ITEMS.register("axe_diamond",
            () -> new HammerHeadItem(Tiers.DIAMOND, HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), new Item.Properties()));

    @ItemModelGen(path = "attachments")
    public static final DeferredHolder<Item, Item> AXE_NETHERITE = ITEMS.register("axe_netherite",
            () -> new HammerHeadItem(Tiers.NETHERITE, HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
