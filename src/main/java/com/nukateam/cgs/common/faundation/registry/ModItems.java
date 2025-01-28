
package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.data.DataGen;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import com.nukateam.ntgl.common.data.attachment.impl.Attachment;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import com.nukateam.ntgl.common.foundation.item.attachment.ScopeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.nukateam.cgs.common.faundation.registry.Attachments.SHORT_SCOPE;
import static com.nukateam.cgs.common.faundation.registry.CgsAttachmentTypes.ENGINE;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gunsmithing.MOD_ID);

    @DataGen
    public static final RegistryObject<Item> BARREL = registerItem("barrel");

    @DataGen
    public static final RegistryObject<Item> STURDY_BARREL = registerItem("barrel_sturdy");

    @DataGen
    public static final RegistryObject<Item> PRESS_FORM_7MM = registerItem("press_form_7mm");

    @DataGen
    public static final RegistryObject<Item> SHELL_7MM = registerItem("shell_7mm");

    @DataGen
    public static final RegistryObject<Item> ROUND_7MM = registerAmmo("round_7mm");

    @DataGen
    public static final RegistryObject<Item> ROUND_10MM = registerAmmo("round_10mm");

    @DataGen
    public static final RegistryObject<Item> STEAM_ENGINE = ITEMS.register("steam_engine",
        () -> new AttachmentItem<>(ENGINE, GenericAttachment.create(), new Item.Properties().stacksTo(1)));

    @DataGen
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
