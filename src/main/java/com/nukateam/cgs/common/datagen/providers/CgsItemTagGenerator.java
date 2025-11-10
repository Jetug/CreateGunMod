package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.nukateam.cgs.common.faundation.registry.CgsBlocks.RAW_LEAD_BLOCK;

public class CgsItemTagGenerator extends ItemTagsProvider {
    public CgsItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, DataGenConfig.DATA_MOD_ID, existingFileHelper);
    }

//    public static TagKey<Item> createItemTag(String path) {
//        return ForgeRegistries.ITEMS.tags()
//                .createOptionalTagKey(ResourceLocation.tryBuild("create", path), Collections.emptySet());
//    }

    private TagKey<Item> LEAD_ORE = AllTags.forgeItemTag("ores/lead");
    private TagKey<Item> ORES = AllTags.forgeItemTag("ores");
    private TagKey<Item> RAW_ORE = AllTags.forgeItemTag("raw_lead/lead");
    private TagKey<Item> ORES_IN_GROUND_STONE = AllTags.forgeItemTag("ores_in_ground/stone");
    private TagKey<Item> ORES_IN_GROUND_DEEPSLATE = AllTags.forgeItemTag("ores_in_ground/deepslate");
    private TagKey<Item> RAW_MATERIALS = AllTags.forgeItemTag("raw_materials");
    private TagKey<Item> RAW_LEAD = AllTags.forgeItemTag("raw_materials/lead");
    private TagKey<Item> STORAGE_BLOCKS = AllTags.forgeItemTag("storage_blocks");
    private TagKey<Item> LEAD_STORAGE_BLOCKS = AllTags.forgeItemTag("storage_blocks/raw_lead");
    private TagKey<Item> INGOTS = AllTags.forgeItemTag("ingots");
    private TagKey<Item> LEAD_INGOTS = AllTags.forgeItemTag("ingots/lead");
    private TagKey<Item> STEEL_INGOTS = AllTags.forgeItemTag("ingots/steel");
    private TagKey<Item> NUGGETS = AllTags.forgeItemTag("nuggets");
    private TagKey<Item> LEAD_NUGGETS = AllTags.forgeItemTag("nuggets/lead");

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ORES).add(
                CgsBlocks.LEAD_ORE.get().asItem(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get().asItem());

        this.tag(LEAD_ORE).add(
                CgsBlocks.LEAD_ORE.get().asItem(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get().asItem());

        this.tag(RAW_ORE).add(RAW_LEAD_BLOCK.get().asItem());
        this.tag(ORES_IN_GROUND_STONE).add(CgsBlocks.LEAD_ORE.get().asItem());
        this.tag(ORES_IN_GROUND_DEEPSLATE).add(CgsBlocks.DEEPSLATE_LEAD_ORE.get().asItem());
        this.tag(STORAGE_BLOCKS).add(RAW_LEAD_BLOCK.get().asItem());
        this.tag(LEAD_STORAGE_BLOCKS).add(RAW_LEAD_BLOCK.get().asItem());

        this.tag(RAW_MATERIALS).add(CgsItems.RAW_LEAD.get());
        this.tag(RAW_LEAD).add(CgsItems.RAW_LEAD.get());
        this.tag(INGOTS).add(CgsItems.LEAD_INGOT.get(), CgsItems.STEEL_INGOT.get());
        this.tag(LEAD_INGOTS).add(CgsItems.LEAD_INGOT.get());
        this.tag(STEEL_INGOTS).add(CgsItems.STEEL_INGOT.get());
        this.tag(NUGGETS).add(CgsItems.LEAD_NUGGET.get(), CgsItems.STEEL_NUGGET.get());
        this.tag(LEAD_NUGGETS).add(CgsItems.LEAD_NUGGET.get());
    }
}
