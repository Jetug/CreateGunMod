package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.util.TagsKeys;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.UnaryOperator;

public class CgsSequencedAssemblyRecipeGen extends SequencedAssemblyRecipeGen {

    public CgsSequencedAssemblyRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    BaseRecipeProvider.GeneratedRecipe REVOLVER_ROUND = this.create("revolver_round", (b) -> b
            .require(TagsKeys.brassSheet())
            .transitionTo(CgsItems.SHELL_10MM.get())
            .addOutput(new ItemStack(CgsItems.ROUND_10MM.get(), 8), 1.0F)
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(CgsItems.PRESS_FORM_10MM.get()))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
            .loops(1));

    BaseRecipeProvider.GeneratedRecipe GATLING_ROUND = this.create("gatling_round", (b) -> {
        return b.require(TagsKeys.brassSheet())
                .transitionTo(CgsItems.SHELL_7MM.get())
                .addOutput(new ItemStack(CgsItems.ROUND_7MM.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(CgsItems.PRESS_FORM_7MM.get()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.copperSheet()))
                .loops(1);
    });


    BaseRecipeProvider.GeneratedRecipe SHOTGUN_SHELL = this.create("shotgun_shell", (b) -> {
        return b.require(TagsKeys.brassSheet())
                .transitionTo(CgsItems.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsItems.SHOTGUN_SHELL.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.toolNotConsumed().require(CgsItems.SHOTGUN_PRESS_FORM.get()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_BLANK = this.create("shotgun_round_blank", (b) -> {
        return b.require(CgsItems.SHOTGUN_SHELL.get())
                .transitionTo(CgsItems.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsItems.SHOTGUN_ROUND_BLANK.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Items.PAPER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND = this.create("shotgun_round", (b) -> {
        return b.require(CgsItems.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsItems.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsItems.SHOTGUN_ROUND.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET = this.create("rocket", (b) -> {
        return b.require(AllBlocks.FLUID_PIPE.get())
                .transitionTo(AllBlocks.FLUID_PIPE.get())
                .addOutput(new ItemStack(CgsItems.ROCKET.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.require(Blocks.TNT))
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.require(TagsKeys.brassSheet()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET_SMALL = this.create("rocket_small", (b) -> {
        return b.require(TagsKeys.ironSheet())
                .transitionTo(AllItems.IRON_SHEET)
                .addOutput(new ItemStack(CgsItems.SMALL_ROCKET.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.brassSheet()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe PAPER_CARTRIDGE = this.create("paper_cartridge", (b) -> {
        return b.require(Items.PAPER)
                .transitionTo(Items.PAPER)
                .addOutput(new ItemStack(CgsItems.PAPER_CARTRIDGE.get(), 3), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe NAIL = this.create("nail", (b) -> {
        return b.require(TagsKeys.ironNugget())
                .transitionTo(Items.IRON_NUGGET)
                .addOutput(new ItemStack(CgsItems.NAIL.get(), 6), 1.0F)
                .addStep(CuttingRecipe::new, (rb) -> rb)
                .addStep(PressingRecipe::new, (rb) -> rb)
                .loops(1);
    });
}
