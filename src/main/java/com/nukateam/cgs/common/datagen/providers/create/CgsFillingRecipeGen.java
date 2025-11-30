package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

public class CgsFillingRecipeGen extends FillingRecipeGen {
    public CgsFillingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    GeneratedRecipe WATER_CONTAINER = create("water_container",
            b -> b.require(Fluids.WATER, 1000)
            .require(CgsItems.EMPTY_CONTAINER.get())
            .output(CgsItems.WATER_CONTAINER.get()));

    GeneratedRecipe LAVA_CONTAINER = create("lava_container",
            b -> b.require(Fluids.LAVA, 1000)
            .require(CgsItems.EMPTY_CONTAINER.get())
            .output(CgsItems.LAVA_CONTAINER.get()));
}
