package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class CgsPressingRecipeGen extends PressingRecipeGen {
    public CgsPressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe STEEL_SHEET = create("steel_ingot", b -> b
            .require(CgsItems.STEEL_INGOT.get())
            .output(CgsItems.STEEL_SHEET.get()));

}
