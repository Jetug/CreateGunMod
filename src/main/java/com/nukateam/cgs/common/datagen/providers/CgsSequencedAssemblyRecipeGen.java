package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.util.TagsKeys;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class CgsSequencedAssemblyRecipeGen extends SequencedAssemblyRecipeGen {

    public CgsSequencedAssemblyRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    BaseRecipeProvider.GeneratedRecipe REVOLVER_ROUND = this.create("revolver_round", (b) -> b
            .require(TagsKeys.brassSheet())
            .transitionTo(CgsAmmo.REVOLVER_SHELL.get())
            .addOutput(new ItemStack(CgsAmmo.REVOLVER_ROUND.get(), 8), 1.0F)
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(CgsItems.PRESS_FORM_REVOLVER.get()))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
            .loops(1));

    BaseRecipeProvider.GeneratedRecipe GATLING_ROUND = this.create("gatling_round", (b) -> {
        return b.require(TagsKeys.brassSheet())
                .transitionTo(CgsAmmo.GATLING_SHELL.get())
                .addOutput(new ItemStack(CgsAmmo.GATLING_ROUND.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.toolNotConsumed().require(CgsItems.PRESS_FORM_GATLING.get()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.copperSheet()))
                .loops(1);
    });


    BaseRecipeProvider.GeneratedRecipe SHOTGUN_SHELL = this.create("shotgun_shell", (b) -> {
        return b.require(TagsKeys.brassSheet())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_SHELL.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.toolNotConsumed().require(CgsItems.SHOTGUN_PRESS_FORM.get()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_BLANK = this.create("shotgun_round_blank", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_SHELL.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND_BLANK.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Items.PAPER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND = this.create("shotgun_round", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET = this.create("rocket", (b) -> {
        return b.require(AllBlocks.FLUID_PIPE.get())
                .transitionTo(AllBlocks.FLUID_PIPE.get())
                .addOutput(new ItemStack(CgsAmmo.ROCKET.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.require(Blocks.TNT))
                .addStep(DeployerApplicationRecipe::new, (rb) ->
                        rb.require(TagsKeys.brassSheet()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET_SMALL = this.create("rocket_small", (b) -> {
        return b.require(TagsKeys.ironSheet())
                .transitionTo(AllItems.IRON_SHEET)
                .addOutput(new ItemStack(CgsAmmo.SMALL_ROCKET.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.brassSheet()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe PAPER_CARTRIDGE = this.create("paper_cartridge", (b) -> {
        return b.require(Items.PAPER)
                .transitionTo(Items.PAPER)
                .addOutput(new ItemStack(CgsAmmo.PAPER_CARTRIDGE.get(), 3), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagsKeys.leadNugget()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe NAIL = this.create("nail", (b) -> {
        return b.require(TagsKeys.ironNugget())
                .transitionTo(Items.IRON_NUGGET)
                .addOutput(new ItemStack(CgsAmmo.NAIL.get(), 6), 1.0F)
                .addStep(CuttingRecipe::new, (rb) -> rb)
                .addStep(PressingRecipe::new, (rb) -> rb)
                .loops(1);
    });
}
