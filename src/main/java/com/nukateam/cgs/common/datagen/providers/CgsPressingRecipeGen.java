package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.api.data.recipe.PressingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;

public class CgsPressingRecipeGen extends PressingRecipeGen {
    public CgsPressingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe STEEL_SHEET = create("steel_ingot", b -> b
            .require(CgsItems.STEEL_INGOT.get())
            .output(CgsItems.STEEL_SHEET.get()));

}
