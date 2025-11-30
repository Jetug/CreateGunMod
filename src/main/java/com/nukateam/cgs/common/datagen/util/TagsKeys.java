package com.nukateam.cgs.common.datagen.util;

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

    //BLOCK TAGS
    public static final TagKey<Block> ORES                         = forgeBlockTag("ores");
    public static final TagKey<Block> LEAD_ORES                    = forgeBlockTag("ores/lead");
    public static final TagKey<Block> SULFUR_ORES                  = forgeBlockTag("ores/sulfur");
    public static final TagKey<Block> ORES_IN_GROUND_STONE         = forgeBlockTag("ores_in_ground/stone");
    public static final TagKey<Block> ORES_IN_GROUND_DEEPSLATE     = forgeBlockTag("ores_in_ground/deepslate");
    public static final TagKey<Block> ORES_IN_GROUND_NETHERRACK    = forgeBlockTag("ores_in_ground/netherrack");
    public static final TagKey<Block> STORAGE_BLOCKS               = forgeBlockTag("storage_blocks");
    public static final TagKey<Block> RAW_LEAD_STORAGE_BLOCKS      = forgeBlockTag("storage_blocks/raw_lead");
    public static final TagKey<Block> LEAD_STORAGE_BLOCKS          = forgeBlockTag("storage_blocks/lead");
    public static final TagKey<Block> STEEL_STORAGE_BLOCKS         = forgeBlockTag("storage_blocks/steel");

    //ITEM TAGS
    public static final TagKey<Item> BRASS_STORAGE_BLOCKS = forgeItemTag("storage_blocks/brass");
    public static final TagKey<Item> BRASS_SHEET = forgeItemTag("plates/brass");
    public static final TagKey<Item> COPPER_SHEET = forgeItemTag("plates/copper");
    public static final TagKey<Item> IRON_SHEET = forgeItemTag("plates/iron");
    public static final TagKey<Item> STEEL_SHEET = forgeItemTag("plates/steel");
    public static final TagKey<Item> STEEL_INGOT = forgeItemTag("ingots/steel");
    public static final TagKey<Item> LEAD_NUGGET =forgeItemTag("nuggets/lead");
    public static final TagKey<Item> STEEL_NUGGET =forgeItemTag("nuggets/steel");
    public static final TagKey<Item> IRON_NUGGET =forgeItemTag("nuggets/iron");
    public static final TagKey<Item> COPPER_NUGGET =forgeItemTag("nuggets/copper");
}
