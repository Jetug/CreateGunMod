package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.util.TagsKeys;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CgsRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public static final String INGOT_TAG = "ingots/";
    public static final String NUGGET_TAG = "nuggets/";
    public static final String LEAD = "lead";

    public CgsRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        buildOreRecipes(writer,
                CgsBlocks.LEAD_ORE.get(), CgsBlocks.DEEPSLATE_LEAD_ORE.get(),
                CgsBlocks.LEAD_BLOCK.get(), CgsBlocks.RAW_LEAD_BLOCK.get(),
                CgsItems.RAW_LEAD.get(), CgsItems.LEAD_INGOT.get(), CgsItems.LEAD_NUGGET.get());

        var leadNugget = AllTags.forgeItemTag(NUGGET_TAG + LEAD);

        //GUNS
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsWeapons.FLINTLOCK.get())
                .pattern("   ")
                .pattern("BAF")
                .pattern("  L")
                .define('B', CgsItems.BARREL.get())
                .define('A', AllItems.ANDESITE_ALLOY.get())
                .define('L', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('F', Items.FLINT_AND_STEEL)
                .unlockedBy(getHasName(CgsItems.BARREL.get()), has(CgsItems.BARREL.get()))
                .save(writer, getId(CgsWeapons.FLINTLOCK.get()));

        //AMMO
        ammo(writer, leadNugget);
//
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, Items.GUNPOWDER, 3)
                .requires(CgsItems.CHARCOAL_DUST.get())
                .requires(CgsItems.SULFUR.get())
                .requires(CgsItems.SELITRA.get())
                .requires(Items.GUNPOWDER)
                .unlockedBy(getHasName(CgsItems.CHARCOAL_DUST.get()), has(CgsItems.CHARCOAL_DUST.get()))
                .save(writer, getId(Items.GUNPOWDER));


        //ATTACHMENTS
        attachments(writer);

    }

    private static void ammo(Consumer<FinishedRecipe> writer, TagKey<Item> leadNugget) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAmmo.PAPER_CARTRIDGE.get(), 3)
                .pattern("L")
                .pattern("G")
                .pattern("P")
                .define('L', leadNugget)
                .define('G', Tags.Items.GUNPOWDER)
                .define('P', Items.PAPER)
                .unlockedBy(getHasName(CgsItems.LEAD_NUGGET.get()), has(CgsItems.LEAD_NUGGET.get()))
                .save(writer, getId(CgsAmmo.PAPER_CARTRIDGE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAmmo.NAIL.get(), 9)
                .pattern("N")
                .pattern("N")
                .pattern("N")
                .define('N', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(writer, getId(CgsAmmo.NAIL.get()));
    }

    private static void attachments(Consumer<FinishedRecipe> writer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, CgsAttachments.SCOPE.get(), 1)
                .requires(AllItems.BRASS_SHEET.get())
                .requires(Items.SPYGLASS)
                .requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(CgsItems.LEAD_NUGGET.get()), has(CgsItems.LEAD_NUGGET.get()))
                .save(writer, getId(CgsAttachments.SCOPE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.STOCK.get(), 1)
                .pattern("WLW")
                .pattern(" WW")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .unlockedBy(getHasName(CgsItems.LEAD_NUGGET.get()), has(CgsItems.LEAD_NUGGET.get()))
                .save(writer, getId(CgsAttachments.STOCK.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.REVOLVER_LONG_BARREL.get(), 1)
                .pattern("BB")
                .pattern("LL")
                .pattern(" W")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('B', CgsItems.BARREL.get())
                .unlockedBy(getHasName(CgsItems.LEAD_NUGGET.get()), has(CgsItems.LEAD_NUGGET.get()))
                .save(writer, getId(CgsAttachments.REVOLVER_LONG_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.SHOTGUN_LONG_BARREL.get(), 1)
                .pattern("BB ")
                .pattern("LWW")
                .pattern("BB ")
                .define('L', AllItems.BRASS_SHEET)
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('B', CgsItems.STURDY_BARREL.get())
                .unlockedBy(getHasName(CgsItems.STURDY_BARREL.get()), has(CgsItems.STURDY_BARREL.get()))
                .save(writer, getId(CgsAttachments.SHOTGUN_LONG_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.SHOTGUN_SPREAD_BARREL.get(), 1)
                .pattern("BLB")
                .define('L', AllItems.IRON_SHEET.get())
                .define('B', CgsItems.STURDY_BARREL.get())
                .unlockedBy(getHasName(CgsItems.STURDY_BARREL.get()), has(CgsItems.STURDY_BARREL.get()))
                .save(writer, getId(CgsAttachments.SHOTGUN_SPREAD_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.FLINTLOCK_LONG_BARREL.get(), 1)
                .pattern("BB")
                .pattern("LL")
                .define('L', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .define('B', CgsItems.BARREL.get())
                .unlockedBy(getHasName(CgsItems.BARREL.get()), has(CgsItems.STURDY_BARREL.get()))
                .save(writer, getId(CgsAttachments.FLINTLOCK_LONG_BARREL.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.HAMMER_CHAMBER.get(), 1)
                .pattern("LBL")
                .pattern("ACC")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('B', AllItems.STURDY_SHEET.get())
                .define('C', AllItems.COPPER_SHEET.get())
                .define('A', AllItems.ANDESITE_ALLOY.get())
                .unlockedBy(getHasName(AllItems.STURDY_SHEET.get()), has(AllItems.STURDY_SHEET.get()))
                .save(writer, getId(CgsAttachments.HAMMER_CHAMBER.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.LAUNCHER_BAYONET.get(), 1)
                .pattern("IIL")
                .pattern(" II")
                .define('L', AllItems.BRASS_SHEET.get())
                .define('I', AllItems.IRON_SHEET.get())
                .unlockedBy(getHasName(AllItems.BRASS_SHEET.get()), has(AllItems.BRASS_SHEET.get()))
                .save(writer, getId(CgsAttachments.LAUNCHER_BAYONET.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.HAMMER_STONE.get(), 1)
                .pattern("BB ")
                .pattern("BBB")
                .pattern("BB ")
                .define('B', Tags.Items.STONE)
                .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.HAMMER_STONE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.HAMMER_IRON.get(), 1)
                .pattern("II ")
                .pattern("IBI")
                .pattern("II ")
                .define('B', Blocks.IRON_BLOCK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.HAMMER_IRON.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.HAMMER_DIAMOND.get(), 1)
                .pattern("II ")
                .pattern("IBI")
                .pattern("II ")
                .define('B', Blocks.DIAMOND_BLOCK)
                .define('I', Items.DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.HAMMER_DIAMOND.get()));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(CgsAttachments.HAMMER_DIAMOND.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.COMBAT,
                        CgsAttachments.HAMMER_NETHERITE.get())
                .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(writer, getId(CgsAttachments.HAMMER_NETHERITE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.AXE_STONE.get(), 1)
                .pattern("BB")
                .pattern("BB")
                .pattern("B ")
                .define('B', Tags.Items.STONE)
                .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.AXE_STONE.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.AXE_IRON.get(), 1)
                .pattern("II")
                .pattern("IB")
                .pattern("I ")
                .define('B', Blocks.IRON_BLOCK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.AXE_IRON.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAttachments.AXE_DIAMOND.get(), 1)
                .pattern("II")
                .pattern("IB")
                .pattern("I ")
                .define('B', Blocks.DIAMOND_BLOCK)
                .define('I', Items.DIAMOND)
                .unlockedBy(getHasName(Blocks.IRON_BLOCK), has(Blocks.IRON_BLOCK))
                .save(writer, getId(CgsAttachments.AXE_DIAMOND.get()));

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(CgsAttachments.AXE_DIAMOND.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.COMBAT,
                        CgsAttachments.AXE_NETHERITE.get())
                .unlocks(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(writer, getId(CgsAttachments.AXE_NETHERITE.get()));


        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsAmmo.SPEAR.get(), 4)
                .pattern("II ")
                .pattern("IW ")
                .pattern("  W")
                .define('I', AllItems.IRON_SHEET)
                .define('W', AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .unlockedBy(getHasName( AllItems.IRON_SHEET), has( AllItems.IRON_SHEET))
                .save(writer, getId(CgsAmmo.SPEAR.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CgsWeapons.GRENADE.get(), 1)
                .pattern(" CЖ")
                .pattern("IGB")
                .define('I', AllItems.IRON_SHEET)
                .define('B', AllItems.BRASS_SHEET)
                .define('C', AllItems.COPPER_NUGGET)
                .define('Ж', AllBlocks.COGWHEEL)
                .define('G', Items.GUNPOWDER)
                .unlockedBy(getHasName(AllItems.IRON_SHEET), has(AllItems.IRON_SHEET))
                .save(writer, getId(CgsWeapons.GRENADE.get()));
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

    public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.tags().createOptionalTagKey(id, Collections.emptySet());
    }

    private static @NotNull ResourceLocation getId(ItemLike item) {
        return new ResourceLocation(DataGenConfig.DATA_MOD_ID, getItemName(item));
    }

    public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("forge", path));
    }

//    public static <T> TagKey<T> mcTag(IForgeRegistry<T> registry, String path) {
//        return optionalTag(registry, new ResourceLocation("minecraft", path));
//    }

    public static TagKey<Block> forgeBlockTag(String path) {
        return forgeTag(ForgeRegistries.BLOCKS, path);
    }

    public static TagKey<Item> forgeItemTag(String path) {
        return forgeTag(ForgeRegistries.ITEMS, path);
    }

}
