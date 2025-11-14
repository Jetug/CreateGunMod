package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CgsCrushingRecipeGen extends CrushingRecipeGen {
    public CgsCrushingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe CHARCOIL_DUST = create(() -> Items.CHARCOAL, b -> b.duration(100)
            .output(CgsItems.CHARCOAL_DUST.get(), 1));

}
