package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModBlocks;
import com.nukateam.ntgl.common.foundation.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Gunsmithing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LEAD_ORE.get(),
                        ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                        ModBlocks.LEAD_BLOCK.get(),
                        ModBlocks.RAW_LEAD_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LEAD_ORE.get(),
                        ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                        ModBlocks.LEAD_BLOCK.get(),
                        ModBlocks.RAW_LEAD_BLOCK.get());
    }
}
