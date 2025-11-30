package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import static com.nukateam.cgs.common.datagen.util.TagsKeys.*;


public class CgsMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
	public CgsMechanicalCraftingRecipeGen(PackOutput output) {
		super(output, Gunsmithing.MOD_ID);
	}

	BaseRecipeProvider.GeneratedRecipe
		SHOTGUN = create(CgsWeapons.SHOTGUN::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', STEEL_SHEET)
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('L', BRASS_SHEET)
					.patternLine("BBAL")
					.patternLine("BBAL")
					.patternLine("WW W")
					.disallowMirrored()),

		GATLING = create(CgsWeapons.GATLING::get).returns(1)
			.recipe(b -> b
					.key('B', STEEL_SHEET)
					.key('I', STEEL_INGOT)
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('C', AllBlocks.COGWHEEL.get())
					.key('P', AllItems.PRECISION_MECHANISM.get())
					.key('L', BRASS_SHEET)
					.patternLine("BBILLL")
					.patternLine("BBICCL")
					.patternLine("BBIPAL")
					.patternLine("BBIAAL")
					.disallowMirrored()),


		REVOLVER = create(CgsWeapons.REVOLVER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', STEEL_SHEET)
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('C', AllBlocks.COGWHEEL.get())
					.key('I', STEEL_INGOT)
					.key('L', BRASS_SHEET)
					.patternLine("BBIC")
					.patternLine("WALL")
					.patternLine("   W")
					.disallowMirrored())
			,

		NAILGUN = create(CgsWeapons.NAILGUN::get).returns(1)
				.recipe(b -> b
				.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
				.key('M', AllItems.PRECISION_MECHANISM.get())
				.key('P', AllBlocks.FLUID_PIPE)
				.key('T', AllItems.COPPER_BACKTANK)
				.key('A', AllItems.ANDESITE_ALLOY.get())
				.key('F', AllBlocks.FLUID_VALVE)
				.key('L', BRASS_SHEET)
				.key('I', IRON_SHEET)
				.key('B', Ingredient.of(AllTags.AllItemTags.TOOLBOXES.tag))
						.patternLine("IIBAL ")
						.patternLine("PPFTMW")
						.patternLine(" LLLW ")
						.disallowMirrored()),

	LAUNCHER = create(CgsWeapons.LAUNCHER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('L', BRASS_SHEET)
					.key('I', STEEL_SHEET)
					.key('P', AllBlocks.FLUID_PIPE.get())
					.key('C', AllBlocks.CHUTE)
					.patternLine("APLLPA")
					.patternLine("ICCCCI")
					.patternLine("WAWWA ")
					.patternLine(" WW   ")
					.disallowMirrored()),

	BLAZEGUN = create(CgsWeapons.BLAZEGUN::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('L', BRASS_SHEET)
					.key('P', AllBlocks.FLUID_PIPE.get())
					.key('C', AllBlocks.CHUTE)
					.key('B', AllBlocks.BLAZE_BURNER)
					.key('T', AllBlocks.FLUID_TANK)
					.patternLine("  ALW ")
					.patternLine("LCCBLW")
					.patternLine(" PPTW ")
					.disallowMirrored()),

	HAMMER = create(CgsWeapons.HAMMER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('L', BRASS_STORAGE_BLOCKS)
					.key('P', AllBlocks.MECHANICAL_PISTON.get())
					.key('T', AllItems.COPPER_BACKTANK)
					.patternLine("PLT")
					.patternLine("WA ")
					.patternLine(" A ")
					.patternLine(" A ")
					.disallowMirrored()),

	//ATTACHMENTS
		ENGINE = create(CgsAttachments.STEAM_ENGINE::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('P', AllBlocks.FLUID_PIPE)
						.key('T', AllBlocks.FLUID_TANK)
						.key('E', AllBlocks.STEAM_ENGINE)
						.key('F', AllBlocks.FLYWHEEL)
						.key('L', BRASS_SHEET)
						.key('B', Items.BLAST_FURNACE)
						.patternLine(" MP ")
						.patternLine("FETP")
						.patternLine(" LBL")
						.disallowMirrored())
		,

		AUTO = create(CgsAttachments.REVOLVER_AUTO::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('S', AllBlocks.SHAFT.get())
						.key('C', AllBlocks.COGWHEEL.get())
						.key('L', BRASS_SHEET)
						.patternLine("SAMC")
						.patternLine("SLL ")
						.disallowMirrored())
		,

		BELT = create(CgsAttachments.REVOLVER_BELT::get).returns(1)
				.recipe(b -> b
						.key('S', STEEL_SHEET)
						.key('B', AllItems.BRASS_NUGGET.get())
						.patternLine("BB")
						.patternLine("SS")
						.patternLine("BB")
						.patternLine("SS")
						.patternLine("BB")
						.disallowMirrored())
		,

		DRUMS = create(CgsAttachments.SHOTGUN_DRUM::get).returns(1)
				.recipe(b -> b
						.key('S', STEEL_SHEET)
						.key('C', AllBlocks.COGWHEEL.get())
						.key('L', BRASS_SHEET)
						.patternLine("LSLLSL")
						.patternLine("SCSSCS")
						.patternLine("LSLLSL")
						.disallowMirrored()),

		PUMPS = create(CgsAttachments.SHOTGUN_PUMP::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('L', BRASS_SHEET)
						.patternLine("LMLLML")
						.patternLine("L LL L")
						.patternLine("L LL L")
						.patternLine("LALLAL")
						.disallowMirrored()),

		BIG_DRUM = create(CgsAttachments.GATLING_DRUM::get).returns(1)
				.recipe(b -> b
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('C', AllBlocks.LARGE_COGWHEEL.get())
						.key('L', BRASS_SHEET)
						.patternLine("  L  ")
						.patternLine(" LAL ")
						.patternLine("LACAL")
						.patternLine(" LAL ")
						.patternLine("  L  ")
						.disallowMirrored())
		,

		SPLITTER = create(CgsAttachments.NAILGUN_SPLITTER::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('P', AllBlocks.FLUID_PIPE)
						.key('I', IRON_SHEET)
						.key('C', AllBlocks.CHUTE.get())
						.patternLine("PIP ")
						.patternLine("IMIC")
						.patternLine("PIP ")
						.disallowMirrored())
		,

		BALLISTAZOOKA = create(CgsAttachments.BALLISTAZOOKA::get).returns(1)
				.recipe(b -> b
						.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
						.key('F', AllBlocks.FLYWHEEL)
						.key('I', IRON_SHEET)
						.key('L', BRASS_SHEET)
						.key('S', Items.STRING)
						.patternLine("FIISS")
						.patternLine("WWWWL")
						.patternLine("FIISS")
						.disallowMirrored()),

		AUTO_LAUNCHER = create(CgsAttachments.AUTO_LAUNCHER::get).returns(1)
				.recipe(b -> b
						.key('B', AllTags.AllItemTags.TOOLBOXES.tag)
						.key('P', AllBlocks.FLUID_PIPE.get())
						.key('p', AllItems.PRECISION_MECHANISM.get())
						.key('T', AllItems.COPPER_BACKTANK.get())
						.key('c', AllBlocks.COGWHEEL.get())
						.key('L', BRASS_SHEET)
						.key('C', COPPER_SHEET)
						.patternLine("LLCC ")
						.patternLine("PPBBp")
						.patternLine("PPccT")
						.disallowMirrored())
		;
}