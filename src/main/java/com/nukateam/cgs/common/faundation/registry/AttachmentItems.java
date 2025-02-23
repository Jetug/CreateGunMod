package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.cgs.common.ntgl.Attachments;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
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

    @ItemModelGen(path = "attachments")
    public static final RegistryObject<Item> STEAM_ENGINE = ITEMS.register("steam_engine",
            () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), new Item.Properties().stacksTo(1)));

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
            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME, GenericAttachment.create(),
                    new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
