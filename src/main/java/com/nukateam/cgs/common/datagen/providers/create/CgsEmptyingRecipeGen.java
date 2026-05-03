package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class CgsEmptyingRecipeGen extends EmptyingRecipeGen {
    public CgsEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe WATER_CONTAINER = create("water_container", b -> b
            .require(CgsItems.WATER_CONTAINER.get())
            .output(Fluids.WATER, 1000)
            .output(CgsItems.EMPTY_CONTAINER.get()));

    GeneratedRecipe LAVA_CONTAINER = create("lava_container", b -> b
            .require(CgsItems.LAVA_CONTAINER.get())
            .output(Fluids.LAVA, 1000)
            .output(CgsItems.EMPTY_CONTAINER.get()));
}