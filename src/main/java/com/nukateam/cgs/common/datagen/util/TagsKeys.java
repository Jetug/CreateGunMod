package com.nukateam.cgs.common.datagen.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import static com.simibubi.create.AllTags.NameSpace.FORGE;

public class TagsKeys {
    public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        var id = FORGE.id(path);
        return TagKey.create(registry.getRegistryKey(), id);
    }

    public static TagKey<Block> forgeBlockTag(String path) {
        return forgeTag(ForgeRegistries.BLOCKS, path);
    }

    public static TagKey<Item> forgeItemTag(String path) {
        return forgeTag(ForgeRegistries.ITEMS, path);
    }

    public static TagKey<Item> BRASS_SHEET = forgeItemTag("plates/brass");
    public static TagKey<Item> COPPER_SHEET = forgeItemTag("plates/copper");
    public static TagKey<Item> IRON_SHEET = forgeItemTag("plates/iron");
    public static TagKey<Item> STEEL_SHEET = forgeItemTag("plates/steel");
    public static TagKey<Item> STEEL_INGOT =forgeItemTag("ingots/steel");

    public static TagKey<Item> steelIngot() {
        return forgeItemTag("ingots/steel");
    }

    public static TagKey<Item> leadNugget() {
        return forgeItemTag("nuggets/lead");
    }

    public static TagKey<Item> steelNugget() {
        return forgeItemTag("nuggets/steel");
    }

    public static TagKey<Item> ironNugget() {
        return forgeItemTag("nuggets/iron");
    }
}
