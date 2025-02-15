
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import com.nukateam.ntgl.common.foundation.item.attachment.ScopeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.nukateam.cgs.common.faundation.registry.Attachments.*;
import static com.nukateam.cgs.common.faundation.registry.CgsAttachmentTypes.ENGINE;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    @ItemModelGen
    public static final RegistryObject<Item> RAW_LEAD = registerItem   ("raw_lead");
    @ItemModelGen
    public static final RegistryObject<Item> LEAD_INGOT = registerItem ("lead_ingot");
    @ItemModelGen
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget");

    @ItemModelGen
    public static final RegistryObject<Item> BARREL = registerItem("barrel");

    @ItemModelGen
    public static final RegistryObject<Item> STURDY_BARREL = registerItem("barrel_sturdy");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_7MM = registerItem("press_form_7mm");

    @ItemModelGen
    public static final RegistryObject<Item> PRESS_FORM_10MM = registerItem("press_form_10mm");

    @ItemModelGen
    public static final RegistryObject<Item> SHELL_7MM = registerItem("shell_7mm");

    @ItemModelGen
    public static final RegistryObject<Item> SHELL_10MM = registerItem("shell_10mm");

    @ItemModelGen
    public static final RegistryObject<Item> ROUND_7MM = registerAmmo("round_7mm");

    @ItemModelGen
    public static final RegistryObject<Item> ROUND_10MM = registerAmmo("round_10mm");

    @ItemModelGen
    public static final RegistryObject<Item> STEAM_ENGINE = ITEMS.register("steam_engine",
        () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), new Item.Properties().stacksTo(1)));

    @ItemModelGen
    public static final RegistryObject<Item> IRON_SIGHT = ITEMS.register("iron_sight",
        () -> new ScopeItem(SHORT_SCOPE, new Item.Properties().stacksTo(1)));

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
