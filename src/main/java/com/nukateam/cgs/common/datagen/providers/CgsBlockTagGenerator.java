package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.simibubi.create.AllTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CgsBlockTagGenerator extends BlockTagsProvider {
    public CgsBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DataGenConfig.DATA_MOD_ID, existingFileHelper);
    }

    private final TagKey<Block> ORES = AllTags.forgeBlockTag("ores");
    private final TagKey<Block> LEAD_ORES = AllTags.forgeBlockTag("ores/lead");
    private final TagKey<Block> SULFUR_ORES = AllTags.forgeBlockTag("ores/sulfur");
    private final TagKey<Block> ORES_IN_GROUND_STONE = AllTags.forgeBlockTag("ores_in_ground/stone");
    private final TagKey<Block> ORES_IN_GROUND_DEEPSLATE = AllTags.forgeBlockTag("ores_in_ground/deepslate");
    private final TagKey<Block> ORES_IN_GROUND_NETHERRACK = AllTags.forgeBlockTag("ores_in_ground/netherrack");
    private final TagKey<Block> STORAGE_BLOCKS = AllTags.forgeBlockTag("storage_blocks");
    private final TagKey<Block> RAW_LEAD_STORAGE_BLOCKS = AllTags.forgeBlockTag("storage_blocks/raw_lead");
    private final TagKey<Block> LEAD_STORAGE_BLOCKS = AllTags.forgeBlockTag("storage_blocks/lead");
    private final TagKey<Block> STEEL_STORAGE_BLOCKS = AllTags.forgeBlockTag("storage_blocks/steel");

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

        this.tag(ORES).add(
                CgsBlocks.LEAD_ORE.get(),
                CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                CgsBlocks.SULFUR_ORE.get());

        this.tag(LEAD_ORES).add(CgsBlocks.LEAD_ORE.get(), CgsBlocks.DEEPSLATE_LEAD_ORE.get());
        this.tag(SULFUR_ORES).add(CgsBlocks.SULFUR_ORE.get());

        this.tag(ORES_IN_GROUND_STONE).add(CgsBlocks.LEAD_ORE.get());
        this.tag(ORES_IN_GROUND_DEEPSLATE).add(CgsBlocks.DEEPSLATE_LEAD_ORE.get());
        this.tag(ORES_IN_GROUND_NETHERRACK).add(CgsBlocks.SULFUR_ORE.get());

        this.tag(STORAGE_BLOCKS).add(
                CgsBlocks.LEAD_BLOCK.get(),
                CgsBlocks.STEEL_BLOCK.get(),
                CgsBlocks.RAW_LEAD_BLOCK.get());

        this.tag(LEAD_STORAGE_BLOCKS).add(CgsBlocks.LEAD_BLOCK.get());
        this.tag(STEEL_STORAGE_BLOCKS).add(CgsBlocks.STEEL_BLOCK.get());
        this.tag(RAW_LEAD_STORAGE_BLOCKS).add(CgsBlocks.RAW_LEAD_BLOCK.get());
    }
}
