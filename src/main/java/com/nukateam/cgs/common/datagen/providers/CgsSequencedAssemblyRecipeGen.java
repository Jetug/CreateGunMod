package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.faundation.registry.ModItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.UnaryOperator;

public class CgsSequencedAssemblyRecipeGen extends CreateRecipeProvider {

//    CreateRecipeProvider.GeneratedRecipe PRECISION_MECHANISM = this.create("precision_mechanism", (b) -> {
//        return b.require(I.goldSheet()).transitionTo(AllItems.INCOMPLETE_PRECISION_MECHANISM.get())
//                .addOutput(AllItems.PRECISION_MECHANISM.get(), 120.0F)
//                .addOutput(AllItems.GOLDEN_SHEET.get(), 8.0F)
//                .addOutput(AllItems.ANDESITE_ALLOY.get(), 8.0F)
//                .addOutput( AllBlocks.COGWHEEL.get(), 5.0F)
//                .addOutput(Items.GOLD_NUGGET, 3.0F)
//                .addOutput(AllBlocks.SHAFT.get(), 2.0F)
//                .addOutput(AllItems.CRUSHED_GOLD.get(), 2.0F)
//                .addOutput(Items.IRON_INGOT, 1.0F)
//                .addOutput(Items.CLOCK, 1.0F).loops(5).addStep(DeployerApplicationRecipe::new, (rb) -> {
//            return rb.require(I.cog());
//        }).addStep(DeployerApplicationRecipe::new, (rb) -> {
//            return rb.require(I.largeCog());
//        }).addStep(DeployerApplicationRecipe::new, (rb) -> {
//            return rb.require(I.ironNugget());
//        });
//    });

    static TagKey<Item> brassSheet() {
        return AllTags.forgeItemTag("plates/brass");
    }

    static TagKey<Item> copperSheet() {
        return AllTags.forgeItemTag("plates/copper");
    }

    static TagKey<Item> leadNugget() {
        return AllTags.forgeItemTag("nuggets/lead");
    }

    static TagKey<Item> ironNugget() {
        return AllTags.forgeItemTag("nuggets/iron");
    }

//    CreateRecipeProvider.GeneratedRecipe REINFORCED_SHEET = this.create("sturdy_sheet", (b) -> {
//        return b.require(AllItems.POWDERED_OBSIDIAN.get())
//                .transitionTo(AllItems.INCOMPLETE_REINFORCED_SHEET.get())
//                .addOutput(AllItems.STURDY_SHEET.get(), 1.0F)
//                .loops(1)
//                .addStep(FillingRecipe::new, (rb) -> rb.require(Fluids.LAVA, 500))
//                .addStep(PressingRecipe::new, (rb) -> rb)
//                .addStep(PressingRecipe::new, (rb) -> rb);
//    });

    CreateRecipeProvider.GeneratedRecipe REVOLVER_ROUND = this.create("revolver_round", (b) -> b
            .require(brassSheet())
            .transitionTo(ModItems.SHELL_10MM.get())
            .addOutput(new ItemStack(ModItems.ROUND_10MM.get(), 8), 1.0F)
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(ModItems.PRESS_FORM_10MM.get()))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(leadNugget()))
            .loops(1));

    CreateRecipeProvider.GeneratedRecipe GATLING_ROUND = this.create("gatling_round", (b) -> {
        return b.require(brassSheet())
                .transitionTo(ModItems.SHELL_7MM.get())
                .addOutput(new ItemStack(ModItems.ROUND_7MM.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(ModItems.PRESS_FORM_7MM.get()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(leadNugget()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(copperSheet()))
                .loops(1);
    });

    CreateRecipeProvider.GeneratedRecipe SHOTGUN_ROUND = this.create("shotgun_round", (b) -> {
        return b.require(brassSheet())
                .transitionTo(ModItems.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(ModItems.SHOTGUN_ROUND.get(), 2), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(ModItems.SHOTGUN_PRESS_FORM.get()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Items.PAPER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(leadNugget()))
                .loops(1);
    });

    CreateRecipeProvider.GeneratedRecipe PAPER_CARTRIDGE = this.create("paper_cartridge", (b) -> {
        return b.require(Items.PAPER)
                .transitionTo(Items.PAPER)
                .addOutput(new ItemStack(ModItems.PAPER_CARTRIDGE.get(), 3), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(leadNugget()))
                .loops(1);
    });

    CreateRecipeProvider.GeneratedRecipe NAIL = this.create("nail", (b) -> {
        return b.require(ironNugget())
                .transitionTo(Items.IRON_NUGGET)
                .addOutput(new ItemStack(ModItems.NAIL.get(), 6), 1.0F)
                .addStep(CuttingRecipe::new, (rb) -> rb)
                .addStep(PressingRecipe::new, (rb) -> rb)
                .loops(1);
    });

//    CreateRecipeProvider.GeneratedRecipe TRACK = this.create("track", (b) -> {
//        return b.require(AllTags.AllItemTags.SLEEPERS.tag).transitionTo(AllItems.INCOMPLETE_TRACK.get()).addOutput(AllBlocks.TRACK.get(), 1.0F).loops(1).addStep(DeployerApplicationRecipe::new, (rb) -> {
//            return rb.require(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(I.ironNugget()), new Ingredient.TagValue(I.zincNugget()))));
//        }).addStep(DeployerApplicationRecipe::new, (rb) -> {
//            return rb.require(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(I.ironNugget()), new Ingredient.TagValue(I.zincNugget()))));
//        }).addStep(PressingRecipe::new, (rb) -> {
//            return rb;
//        });
//    });

    public CgsSequencedAssemblyRecipeGen(PackOutput p_i48262_1_) {
        super(p_i48262_1_);
    }

    protected CreateRecipeProvider.GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        CreateRecipeProvider.GeneratedRecipe generatedRecipe = (c) -> {
            transform.apply(new SequencedAssemblyRecipeBuilder(new ResourceLocation(DataGenConfig.DATA_MOD_ID, name))).build(c);
        };

        this.all.add(generatedRecipe);
        return generatedRecipe;
    }

    public String getName() {
        return "CGS's Sequenced Assembly Recipes";
    }
}
