package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.cgs.common.ntgl.Attachments;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.attachment.impl.Barrel;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.data.attachment.impl.Stock;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import com.nukateam.ntgl.common.foundation.item.attachment.BarrelItem;
import com.nukateam.ntgl.common.foundation.item.attachment.ScopeItem;
import com.nukateam.ntgl.common.foundation.item.attachment.StockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.nukateam.cgs.common.ntgl.Attachments.*;
import static com.nukateam.cgs.common.ntgl.CgsAttachmentTypes.ENGINE;

public class AttachmentItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);


    public static final Item.Properties GUN_PROPERTIES = new Item.Properties().stacksTo(1);
    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> STEAM_ENGINE = ITEMS.register("steam_engine",
            () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), GUN_PROPERTIES));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> IRON_SIGHT = ITEMS.register("iron_sight",
            () -> new ScopeItem(SHORT_SCOPE, new Item.Properties().stacksTo(1)));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> SCOPE = ITEMS.register("scope",
            () -> new ScopeItem(LONG_SCOPE, new Item.Properties().stacksTo(1)));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> STOCK = ITEMS.register("stock",
            () -> new StockItem(Stock.create(), new Item.Properties().stacksTo(1)));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> LONG_BARREL = ITEMS.register("long_barrel",
            () -> new BarrelItem(Barrel.create(5, Attachments.LONG_BARREL), new Item.Properties().stacksTo(1)));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> BELT = ITEMS.register("round_belt",
            () -> new AttachmentItem<>(CgsAttachmentTypes.CHAMBER, GenericAttachment.create(BELT_MODIFIERS),
                    new Item.Properties().stacksTo(1)));

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> AUTO = ITEMS.register("auto_fire",
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME, GenericAttachment.create(AUTO_FIRE),
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN_DRUM = ITEMS.register("shotgun_drum",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(Attachments.SHOTGUN_DRUM),
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN_PUMP = ITEMS.register("shotgun_pump",
            () -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(Attachments.SHOTGUN_PUMP),
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN_LONG_BARREL = ITEMS.register("shotgun_long_barrel",
            () -> new BarrelItem(Barrel.create(10f, Attachments.SHOTGUN_LONG_BARREL), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHOTGUN_SPREAD_BARREL = ITEMS.register("shotgun_spread_barrel",
            () -> new BarrelItem(Barrel.create(1f, Attachments.SHOTGUN_SPREAD_BARREL), new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
