package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CgsCrushingRecipeGen extends CrushingRecipeGen {
    public CgsCrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe CHARCOAL_DUST = create(() -> Items.CHARCOAL, b -> b.duration(100)
            .output(CgsItems.CHARCOAL_DUST.get(), 1));

    GeneratedRecipe SULFUR = create(() -> Blocks.MAGMA_BLOCK, b -> b.duration(100)
            .output(0.1f, CgsItems.SULFUR.get(), 1));

}
