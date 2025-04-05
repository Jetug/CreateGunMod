package com.nukateam.cgs.common.datagen.providers;

import java.util.function.UnaryOperator;

import com.google.common.base.Supplier;
import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.ModGuns;
import com.nukateam.cgs.common.faundation.registry.ModItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;


public class CgsMechanicalCraftingRecipeGen extends CreateRecipeProvider {
	public CgsMechanicalCraftingRecipeGen(PackOutput p_i48262_1_) {
		super(p_i48262_1_);
	}

	GeneratedRecipe
		SHOTGUN = create(ModGuns.SHOTGUN::get).returns(1)
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


		GATLING = create(ModGuns.GATLING::get).returns(1)
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


		REVOLVER = create(ModGuns.REVOLVER::get).returns(1)
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

		NAILGUN = create(ModGuns.NAILGUN::get).returns(1)
				.recipe(b -> b
				.key('W', Ingredient.of(AllTags.AllItemTags.STRIPPED_LOGS.tag))
				.key('M', AllItems.PRECISION_MECHANISM.get())
				.key('P', AllBlocks.FLUID_PIPE)
				.key('T', AllBlocks.FLUID_TANK)
				.key('E', AllBlocks.STEAM_ENGINE)
				.key('F', AllBlocks.FLYWHEEL)
				.key('L', AllItems.BRASS_SHEET.get())
				.key('I', AllItems.IRON_SHEET.get())
						.patternLine(" EFL ")
						.patternLine("PPTMW")
						.patternLine("IILL ")
						.disallowMirrored()),

	//ATTACHMENTS
		//GATLING
		ENGINE = create(AttachmentItems.STEAM_ENGINE::get).returns(1)
				.recipe(b -> b
				.key('M', AllItems.PRECISION_MECHANISM.get())
				.key('P', AllBlocks.FLUID_PIPE)
				.key('T', AllBlocks.FLUID_TANK)
				.key('E', AllBlocks.STEAM_ENGINE)
				.key('F', AllBlocks.FLYWHEEL)
				.key('L', AllItems.BRASS_SHEET.get())
				.key('B', AllBlocks.BLAZE_BURNER.get())
						.patternLine(" MP ")
						.patternLine("FETP")
						.patternLine(" LBL")
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