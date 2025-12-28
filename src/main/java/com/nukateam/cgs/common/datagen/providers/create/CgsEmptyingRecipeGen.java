package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;

public class CgsEmptyingRecipeGen extends EmptyingRecipeGen {
    public CgsEmptyingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
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