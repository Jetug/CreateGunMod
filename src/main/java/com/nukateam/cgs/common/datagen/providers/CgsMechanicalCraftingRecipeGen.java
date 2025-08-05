package com.nukateam.cgs.common.datagen.providers;

import java.util.function.UnaryOperator;

import com.google.common.base.Supplier;
import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.items.ModWeapons;
import com.nukateam.cgs.common.faundation.registry.items.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;


public class CgsMechanicalCraftingRecipeGen extends CreateRecipeProvider {
	public CgsMechanicalCraftingRecipeGen(PackOutput p_i48262_1_) {
		super(p_i48262_1_);
	}

	GeneratedRecipe
		SHOTGUN = create(ModWeapons.SHOTGUN::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', ModItems.STURDY_BARREL.get())
					.key('A', AllItems.ANDESITE_ALLOY.get())
					.key('S', AllItems.STURDY_SHEET.get())
					.key('L', AllItems.BRASS_SHEET.get())
					.patternLine("BSAL")
					.patternLine("BSAL")
					.patternLine("WW W")
					.disallowMirrored()),


		GATLING = create(ModWeapons.GATLING::get).returns(1)
			.recipe(b -> b
					.key('B', ModItems.STURDY_BARREL.get())
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


		REVOLVER = create(ModWeapons.REVOLVER::get).returns(1)
			.recipe(b -> b
					.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
					.key('B', ModItems.BARREL.get())
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

		NAILGUN = create(ModWeapons.NAILGUN::get).returns(1)
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
		;


	RecipeBuilder create(Supplier<ItemLike> result) {
		return new RecipeBuilder(result);
	}

	class RecipeBuilder {
		private String suffix;
		private Supplier<ItemLike> result;
		private int amount;

		public RecipeBuilder(Supplier<ItemLike> result) {
			this.suffix = "";
			this.result = result;
			this.amount = 1;
		}

		RecipeBuilder returns(int amount) {
			this.amount = amount;
			return this;
		}

		RecipeBuilder withSuffix(String suffix) {
			this.suffix = suffix;
			return this;
		}

		GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
			return register(consumer -> {
				var b = builder.apply(MechanicalCraftingRecipeBuilder
								.shapedRecipe(result.get(), amount));

				var location = new ResourceLocation(DataGenConfig.DATA_MOD_ID, "mechanical_crafting/" +
								RegisteredObjects.getKeyOrThrow(result.get().asItem()).getPath() + suffix);
				b.build(consumer, location);
			});
		}
	}

	@Override
	public String getName() {
		return "CGS's Mechanical Crafting Recipes";
	}
}