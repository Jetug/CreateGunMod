package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DataGenConfig.DATA_MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CgsBlocks.LEAD_ORE.get(),
                        CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                        CgsBlocks.LEAD_BLOCK.get(),
                        CgsBlocks.RAW_LEAD_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(CgsBlocks.LEAD_ORE.get(),
                        CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                        CgsBlocks.LEAD_BLOCK.get(),
                        CgsBlocks.RAW_LEAD_BLOCK.get());
    }
}
