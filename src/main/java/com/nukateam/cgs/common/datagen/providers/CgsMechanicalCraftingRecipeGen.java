package com.nukateam.cgs.common.datagen.providers;

import java.util.function.UnaryOperator;

import com.google.common.base.Supplier;
import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;


public class CgsMechanicalCraftingRecipeGen extends MechanicalCraftingRecipeGen {
	public CgsMechanicalCraftingRecipeGen(PackOutput output) {
		super(output, Gunsmithing.MOD_ID);
	}

	BaseRecipeProvider.GeneratedRecipe
		SHOTGUN = create(CgsWeapons.SHOTGUN::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', CgsItems.STURDY_BARREL.get())
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('S', AllItems.STURDY_SHEET.get())
					.key('L', AllItems.BRASS_SHEET.get())
					.patternLine("BSAL")
					.patternLine("BSAL")
					.patternLine("WW W")
					.disallowMirrored()),

		GATLING = create(CgsWeapons.GATLING::get).returns(1)
			.recipe(b -> b
					.key('B', CgsItems.STURDY_BARREL.get())
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('S', AllItems.STURDY_SHEET.get())
					.key('C', AllBlocks.COGWHEEL.get())
					.key('P', AllItems.PRECISION_MECHANISM.get())
					.key('L', AllItems.BRASS_SHEET.get())
					.patternLine("BSLLL")
					.patternLine("BSCCL")
					.patternLine("BSPAL")
					.patternLine("BSAAL")
					.disallowMirrored()),


		REVOLVER = create(CgsWeapons.REVOLVER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', CgsItems.BARREL.get())
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('S', AllItems.STURDY_SHEET.get())
					.key('C', AllBlocks.COGWHEEL.get())
					.key('I', Items.IRON_INGOT)
					.key('L', AllItems.BRASS_SHEET.get())
					.patternLine("BISC")
					.patternLine("WLAL")
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
				.key('L', AllItems.BRASS_SHEET.get())
				.key('I', AllItems.IRON_SHEET.get())
				.key('B', Ingredient.of(AllTags.AllItemTags.TOOLBOXES.tag))
						.patternLine("IIBAL ")
						.patternLine("PPFTMW")
						.patternLine(" LLLW ")
						.disallowMirrored()),

	LAUNCHER = create(CgsWeapons.LAUNCHER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('L', AllItems.BRASS_SHEET.get())
					.key('I', AllItems.IRON_SHEET.get())
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
					.key('L', AllItems.BRASS_SHEET.get())
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
					.key('L', AllBlocks.BRASS_BLOCK.get())
					.key('P', AllBlocks.MECHANICAL_PISTON.get())
					.key('T', AllItems.COPPER_BACKTANK)
					.patternLine("PLT")
					.patternLine("WA ")
					.patternLine(" A ")
					.patternLine(" A ")
					.disallowMirrored()),

	//ATTACHMENTS
		ENGINE = create(AttachmentItems.STEAM_ENGINE::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('P', AllBlocks.FLUID_PIPE)
						.key('T', AllBlocks.FLUID_TANK)
						.key('E', AllBlocks.STEAM_ENGINE)
						.key('F', AllBlocks.FLYWHEEL)
						.key('L', AllItems.BRASS_SHEET.get())
						.key('B', Items.BLAST_FURNACE)
						.patternLine(" MP ")
						.patternLine("FETP")
						.patternLine(" LBL")
						.disallowMirrored())
		,

		AUTO = create(AttachmentItems.REVOLVER_AUTO::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('S', AllBlocks.SHAFT.get())
						.key('C', AllBlocks.COGWHEEL.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.patternLine("SAMC")
						.patternLine("SLL ")
						.disallowMirrored())
		,

		BELT = create(AttachmentItems.REVOLVER_BELT::get).returns(1)
				.recipe(b -> b
						.key('S', AllItems.STURDY_SHEET.get())
						.key('B', AllItems.BRASS_NUGGET.get())
						.patternLine("BB")
						.patternLine("SS")
						.patternLine("BB")
						.patternLine("SS")
						.patternLine("BB")
						.disallowMirrored())
		,

		DRUMS = create(AttachmentItems.SHOTGUN_DRUM::get).returns(1)
				.recipe(b -> b
						.key('S', AllItems.STURDY_SHEET.get())
						.key('C', AllBlocks.COGWHEEL.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.patternLine("LSLLSL")
						.patternLine("SCSSCS")
						.patternLine("LSLLSL")
						.disallowMirrored()),

		PUMPS = create(AttachmentItems.SHOTGUN_PUMP::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.patternLine("LMLLML")
						.patternLine("L LL L")
						.patternLine("L LL L")
						.patternLine("LALLAL")
						.disallowMirrored()),

		BIG_DRUM = create(AttachmentItems.GATLING_DRUM::get).returns(1)
				.recipe(b -> b
						.key('A', AllItems.ANDESITE_ALLOY.get())
						.key('C', AllBlocks.LARGE_COGWHEEL.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.patternLine("  L  ")
						.patternLine(" LAL ")
						.patternLine("LACAL")
						.patternLine(" LAL ")
						.patternLine("  L  ")
						.disallowMirrored())
		,

		SPLITTER = create(AttachmentItems.NAILGUN_SPLITTER::get).returns(1)
				.recipe(b -> b
						.key('M', AllItems.PRECISION_MECHANISM.get())
						.key('P', AllBlocks.FLUID_PIPE)
						.key('I', AllItems.IRON_SHEET.get())
						.key('C', AllBlocks.CHUTE.get())
						.patternLine("PIP ")
						.patternLine("IMIC")
						.patternLine("PIP ")
						.disallowMirrored())
		,

		BALLISTAZOOKA = create(AttachmentItems.BALLISTAZOOKA::get).returns(1)
				.recipe(b -> b
						.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
						.key('F', AllBlocks.FLYWHEEL)
						.key('I', AllItems.IRON_SHEET.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.key('S', Items.STRING)
						.patternLine("FIISS")
						.patternLine("WWWWL")
						.patternLine("FIISS")
						.disallowMirrored()),

		AUTO_LAUNCHER = create(AttachmentItems.AUTO_LAUNCHER::get).returns(1)
				.recipe(b -> b
						.key('B', AllTags.AllItemTags.TOOLBOXES.tag)
						.key('P', AllBlocks.FLUID_PIPE.get())
						.key('p', AllItems.PRECISION_MECHANISM.get())
						.key('T', AllItems.COPPER_BACKTANK.get())
						.key('c', AllBlocks.COGWHEEL.get())
						.key('L', AllItems.BRASS_SHEET.get())
						.key('C', AllItems.COPPER_SHEET.get())
						.patternLine("LLCC ")
						.patternLine("PPBBp")
						.patternLine("PPccT")
						.disallowMirrored())
		;
}