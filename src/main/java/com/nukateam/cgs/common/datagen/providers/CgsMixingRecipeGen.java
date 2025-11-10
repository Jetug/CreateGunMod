package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.util.TagsKeys;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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

}
