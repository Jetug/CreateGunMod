package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.util.TagsKeys;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CgsBlockTagGenerator extends BlockTagsProvider {
    public CgsBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DataGenConfig.DATA_MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                CgsBlocks.LEAD_ORE.get(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                CgsBlocks.RAW_LEAD_BLOCK.get(),
                CgsBlocks.LEAD_BLOCK.get(),
                CgsBlocks.STEEL_BLOCK.get(),
                CgsBlocks.SULFUR_ORE.get(),
                CgsBlocks.GUANO_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                CgsBlocks.LEAD_ORE.get(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                CgsBlocks.RAW_LEAD_BLOCK.get(),
                CgsBlocks.LEAD_BLOCK.get(),
                CgsBlocks.STEEL_BLOCK.get(),
                CgsBlocks.SULFUR_ORE.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                CgsBlocks.GUANO_BLOCK.get());

        this.tag(TagsKeys.ORES).add(
                CgsBlocks.LEAD_ORE.get(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                CgsBlocks.SULFUR_ORE.get());

        this.tag(TagsKeys.LEAD_ORES).add(CgsBlocks.LEAD_ORE.get(), CgsBlocks.DEEPSLATE_LEAD_ORE.get());
        this.tag(TagsKeys.SULFUR_ORES).add(CgsBlocks.SULFUR_ORE.get());

        this.tag(TagsKeys.ORES_IN_GROUND_STONE).add(CgsBlocks.LEAD_ORE.get());
        this.tag(TagsKeys.ORES_IN_GROUND_DEEPSLATE).add(CgsBlocks.DEEPSLATE_LEAD_ORE.get());
        this.tag(TagsKeys.ORES_IN_GROUND_NETHERRACK).add(CgsBlocks.SULFUR_ORE.get());

        this.tag(TagsKeys.STORAGE_BLOCKS).add(
                CgsBlocks.LEAD_BLOCK.get(),
                CgsBlocks.STEEL_BLOCK.get(),
                CgsBlocks.RAW_LEAD_BLOCK.get());

        this.tag(TagsKeys.LEAD_STORAGE_BLOCKS).add(CgsBlocks.LEAD_BLOCK.get());
        this.tag(TagsKeys.STEEL_STORAGE_BLOCKS).add(CgsBlocks.STEEL_BLOCK.get());
        this.tag(TagsKeys.RAW_LEAD_STORAGE_BLOCKS).add(CgsBlocks.RAW_LEAD_BLOCK.get());
    }
}
