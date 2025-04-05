package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.ModBlocks;
import com.nukateam.cgs.common.faundation.registry.ModGuns;
import com.nukateam.cgs.common.faundation.registry.ModItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        buildOreRecipes(writer,
                ModBlocks.LEAD_ORE.get(), ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                ModBlocks.LEAD_BLOCK.get(), ModBlocks.RAW_LEAD_BLOCK.get(),
                ModItems.RAW_LEAD.get(), ModItems.LEAD_INGOT.get(), ModItems.LEAD_NUGGET.get());
    }

    private static void buildOreRecipes(Consumer<FinishedRecipe> writer,
                                        ItemLike ore, ItemLike deepslateOre,
                                        ItemLike block, ItemLike rawBlock,
                                        ItemLike raw, ItemLike ingot, ItemLike nugget) {

        var smeltables = List.of(raw, ore, deepslateOre);

        oreSmelting(writer, smeltables, RecipeCategory.MISC, ingot, 0.25f, 200, "lead");
        oreBlasting(writer, smeltables, RecipeCategory.MISC, ingot, 0.25f, 100, "lead");

        simpleBlock(writer, raw, rawBlock);
        simpleBlock(writer, ingot, block);
        simpleBlock(writer, nugget, ingot);

        fromBlock(writer, rawBlock, raw);
        fromBlock(writer, block, ingot);
        fromBlock(writer, ingot, nugget);

        //GUNS
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModGuns.FLINTLOCK.get())
                .pattern("   ")
                .pattern("BAF")
                .pattern("  L")
                .define('B', ModItems.BARREL.get())
                .define('A', AllItems.ANDESITE_ALLOY.get())
                .define('L', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('F', Items.FLINT_AND_STEEL)
                .unlockedBy(getHasName(ModItems.BARREL.get()), has(ModItems.BARREL.get()))
                .save(writer, getId(ModGuns.FLINTLOCK.get()));

        //AMMO
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BALL.get(), 3)
                .pattern("L")
                .pattern("G")
                .pattern("P")
                .define('L', ModItems.LEAD_NUGGET.get())
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Items.PAPER)
                .unlockedBy(getHasName(ModItems.LEAD_NUGGET.get()), has(ModItems.LEAD_NUGGET.get()))
                .save(writer, getId(ModItems.BALL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NAIL.get(), 9)
                .pattern("N")
                .pattern("N")
                .pattern("N")
                .define('N', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(writer, getId(ModItems.NAIL.get()));

        //ATTACHMENTS
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AttachmentItems.SCOPE.get(), 1)
                .requires(AllItems.BRASS_SHEET.get())
                .requires(Items.SPYGLASS)
                .requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(ModItems.LEAD_NUGGET.get()), has(ModItems.LEAD_NUGGET.get()))
                .save(writer, getId(AttachmentItems.SCOPE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AttachmentItems.STOCK.get(), 1)
                .pattern("WLW")
                .pattern(" WW")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .unlockedBy(getHasName(ModItems.LEAD_NUGGET.get()), has(ModItems.LEAD_NUGGET.get()))
                .save(writer, getId(AttachmentItems.STOCK.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AttachmentItems.LONG_BARREL.get(), 1)
                .pattern("BB")
                .pattern("LL")
                .pattern(" W")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('B', ModItems.BARREL.get())
                .unlockedBy(getHasName(ModItems.LEAD_NUGGET.get()), has(ModItems.LEAD_NUGGET.get()))
                .save(writer, getId(AttachmentItems.LONG_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AttachmentItems.SHOTGUN_LONG_BARREL.get(), 1)
                .pattern("BB ")
                .pattern("LWW")
                .pattern("BB ")
                .define('L', Items.IRON_INGOT)
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('B', ModItems.STURDY_BARREL.get())
                .unlockedBy(getHasName(ModItems.STURDY_BARREL.get()), has(ModItems.STURDY_BARREL.get()))
                .save(writer, getId(AttachmentItems.SHOTGUN_LONG_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AttachmentItems.SHOTGUN_SPREAD_BARREL.get(), 1)
                .pattern("LBL")
                .define('L', AllItems.IRON_SHEET.get())
                .define('B', ModItems.STURDY_BARREL.get())
                .unlockedBy(getHasName(ModItems.STURDY_BARREL.get()), has(ModItems.STURDY_BARREL.get()))
                .save(writer, getId(AttachmentItems.SHOTGUN_SPREAD_BARREL.get()));
    }
    private static void fromBlock(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
                .requires(ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(writer);
    }

    private static void craft(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(writer,  DataGenConfig.DATA_MOD_ID + ":" + getItemName(result) + "_" + "from" + "_" + getItemName(ingredient));
    }

    private static void simpleBlock(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ingredient)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(writer,  DataGenConfig.DATA_MOD_ID + ":" + getItemName(result) + "_" + "from" + "_" + getItemName(ingredient));
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
                    .save(pFinishedRecipeConsumer,  DataGenConfig.DATA_MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private static @NotNull ResourceLocation getId(ItemLike item) {
        return new ResourceLocation(DataGenConfig.DATA_MOD_ID, getItemName(item));
    }
}
