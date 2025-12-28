package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import net.minecraft.data.PackOutput;

public class CgsCuttingRecipeGen extends CuttingRecipeGen {
    public CgsCuttingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

//    GeneratedRecipe LEAD_BALLS = create(Ingredient.of(TagKeys.LEAD_NUGGET), b -> b
//            .duration(20)
//            .output(CgsAmmo.LEAD_BALLS::get, 1));
}
