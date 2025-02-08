package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModBlocks;
import com.nukateam.cgs.common.faundation.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> LEAD_SMELTABLES = List.of(ModItems.RAW_LEAD.get(),
            ModBlocks.LEAD_ORE.get(), ModBlocks.DEEPSLATE_LEAD_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        buildOreRecipes(writer, );
    }

    private static void buildOreRecipes(Consumer<FinishedRecipe> writer, ItemLike ore, ItemLike deepslateOre, ItemLike raw,
                                        ItemLike ingot, ItemLike nugget, ItemLike block, ItemLike rawBlock) {
        oreSmelting(writer, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 200, "lead");
        oreBlasting(writer, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 100, "lead");

        simpleBlock(writer, ModItems.RAW_LEAD.get(), ModBlocks.RAW_LEAD_BLOCK.get());
        simpleBlock(writer, ModItems.LEAD_INGOT.get(), ModBlocks.LEAD_BLOCK.get());
        simpleBlock(writer, ModItems.LEAD_NUGGET.get(), ModItems.LEAD_INGOT.get());

        fromBlock(writer, ModBlocks.RAW_LEAD_BLOCK.get(), ModItems.RAW_LEAD.get());
        fromBlock(writer, ModBlocks.LEAD_BLOCK.get(), ModItems.LEAD_INGOT.get());
        fromBlock(writer, ModItems.LEAD_INGOT.get(), ModItems.LEAD_NUGGET.get());
    }

    private static void fromBlock(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
                .requires(ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(writer);
    }

    private static void simpleBlock(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(writer,  Gunsmithing.MOD_ID + ":" + getItemName(result) + "_" + "from" + "_" + getItemName(ingredient));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                     float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(var itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Gunsmithing.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
