package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import net.minecraft.data.PackOutput;

import static com.nukateam.cgs.common.datagen.util.TagKeys.STEEL_INGOT;

public class CgsPressingRecipeGen extends PressingRecipeGen {
    public CgsPressingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe STEEL_SHEET = create("steel_ingot", b -> b
            .require(STEEL_INGOT)
            .output(CgsItems.STEEL_SHEET.get()));
}
