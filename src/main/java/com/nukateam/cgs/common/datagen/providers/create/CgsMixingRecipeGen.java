package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;

public class CgsMixingRecipeGen extends MixingRecipeGen {
    public CgsMixingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    BaseRecipeProvider.GeneratedRecipe STEEL_INGOT = create("steel_ingot", b -> b
            .require(Tags.Items.INGOTS_IRON)
            .require(CgsItems.CHARCOAL_DUST.get())
            .output(CgsItems.STEEL_INGOT.get())
            .requiresHeat(HeatCondition.SUPERHEATED));

    BaseRecipeProvider.GeneratedRecipe NITER = create("niter", b -> b
            .require(Fluids.WATER, 250)
            .require(CgsItems.GUANO.get())
            .output(CgsItems.NITER.get())
            .output(Items.BONE_MEAL)
            .requiresHeat(HeatCondition.HEATED));

}
