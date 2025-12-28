package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.util.TagKeys;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.simibubi.create.api.data.recipe.SequencedAssemblyRecipeGen;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class CgsSequencedAssemblyRecipeGen extends SequencedAssemblyRecipeGen {

    public CgsSequencedAssemblyRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }

    BaseRecipeProvider.GeneratedRecipe REVOLVER_ROUND_BLANK = this.create("revolver_round_blank", (b) -> b
            .require(TagKeys.BRASS_SHEET)
            .transitionTo(CgsAmmo.REVOLVER_SHELL.get())
            .addStep(DeployerApplicationRecipe::new, noConsumeTool(CgsItems.PRESS_FORM_REVOLVER))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
            .addOutput(new ItemStack(CgsAmmo.REVOLVER_ROUND_BLANK.get(), 8), 1.0F)
            .loops(1));

    BaseRecipeProvider.GeneratedRecipe REVOLVER_ROUND = this.create("revolver_round", (b) -> b
            .require(CgsAmmo.REVOLVER_ROUND_BLANK.get())
            .transitionTo(CgsAmmo.REVOLVER_ROUND_BLANK.get())
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.LEAD_NUGGET))
            .addOutput(new ItemStack(CgsAmmo.REVOLVER_ROUND.get(), 8), 1.0F)
            .loops(1));

    BaseRecipeProvider.GeneratedRecipe REVOLVER_ROUND_PIERCING = this.create("revolver_round_piercing", (b) -> b
            .require(CgsAmmo.REVOLVER_ROUND_BLANK.get())
            .transitionTo(CgsAmmo.REVOLVER_ROUND_BLANK.get())
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.STEEL_NUGGET))
            .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.LEAD_NUGGET))
            .addOutput(new ItemStack(CgsAmmo.REVOLVER_ROUND_PIERCING.get(), 8), 1.0F)
            .loops(1));

    BaseRecipeProvider.GeneratedRecipe GATLING_ROUND_BLANK = this.create("gatling_round_blank", (b) -> {
        return b.require(TagKeys.BRASS_SHEET)
                .transitionTo(CgsAmmo.GATLING_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, noConsumeTool(CgsItems.PRESS_FORM_GATLING))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addOutput(new ItemStack(CgsAmmo.GATLING_ROUND_BLANK.get(), 4), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe GATLING_ROUND = this.create("round_gatling", (b) -> {
        return b.require(CgsAmmo.GATLING_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.GATLING_ROUND_BLANK.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.LEAD_NUGGET))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.COPPER_NUGGET))
                .addOutput(new ItemStack(CgsAmmo.GATLING_ROUND.get(), 1), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe GATLING_ROUND_PIERCING = this.create("round_gatling_piercing", (b) -> {
        return b.require(CgsAmmo.GATLING_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.GATLING_ROUND_BLANK.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.STEEL_NUGGET))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.COPPER_NUGGET))
                .addOutput(new ItemStack(CgsAmmo.GATLING_ROUND_PIERCING.get(), 1), 1.0F)
                .loops(1);
    });

//    BaseRecipeProvider.GeneratedRecipe SHOTGUN_SHELL = this.create("shotgun_shell", (b) -> {
//        return b.require(TagKeys.BRASS_SHEET)
//                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
//                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_SHELL.get(), 4), 1.0F)
//                .addStep(DeployerApplicationRecipe::new, (rb) ->
//                        rb.toolNotConsumed().require(CgsItems.PRESS_FORM_SHOTGUN.get()))
//                .loops(1);
//    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_BLANK = this.create("shotgun_round_blank", (b) -> {
        return b.require(TagKeys.BRASS_SHEET)
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, noConsumeTool(CgsItems.PRESS_FORM_SHOTGUN))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Items.PAPER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND_BLANK.get(), 4), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND = this.create("shotgun_round", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(CgsAmmo.LEAD_BALLS.get()))
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND.get(), 1), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_INCENDIARY = this.create("shotgun_round_incendiary", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Items.BLAZE_POWDER))
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND_INCENDIARY.get(), 1), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_FLECHETTE = this.create("shotgun_round_flechette", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(CgsAmmo.NAIL.get()))
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND_FLECHETTE.get(), 1), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe SHOTGUN_ROUND_FLECHETTE_STEEL = this.create("shotgun_round_flechette_steel", (b) -> {
        return b.require(CgsAmmo.SHOTGUN_ROUND_BLANK.get())
                .transitionTo(CgsAmmo.SHOTGUN_SHELL.get())
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(CgsAmmo.STEEL_NAIL.get()))
                .addOutput(new ItemStack(CgsAmmo.SHOTGUN_ROUND_FLECHETTE_STEEL.get(), 1), 1.0F)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET = this.create("rocket", (b) -> {
        return b.require(AllBlocks.FLUID_PIPE.get())
                .transitionTo(AllBlocks.FLUID_PIPE.get())
                .addOutput(new ItemStack(CgsAmmo.ROCKET.get(), 1), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Blocks.TNT))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.BRASS_SHEET))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe ROCKET_SMALL = this.create("rocket_small", (b) -> {
        return b.require(TagKeys.IRON_SHEET)
                .transitionTo(AllItems.BRASS_SHEET)
                .addOutput(new ItemStack(CgsAmmo.SMALL_ROCKET.get(), 5), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Blocks.TNT))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.BRASS_SHEET))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe PAPER_CARTRIDGE = this.create("paper_cartridge", (b) -> {
        return b.require(Items.PAPER)
                .transitionTo(Items.PAPER)
                .addOutput(new ItemStack(CgsAmmo.PAPER_CARTRIDGE.get(), 4), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(TagKeys.LEAD_NUGGET))
                .loops(1);
    });
    
    BaseRecipeProvider.GeneratedRecipe PAPER_SHOT = this.create("paper_shot", (b) -> {
        return b.require(Items.PAPER)
                .transitionTo(Items.PAPER)
                .addOutput(new ItemStack(CgsAmmo.PAPER_SHOT.get(), 2), 1.0F)
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(Tags.Items.GUNPOWDER))
                .addStep(DeployerApplicationRecipe::new, (rb) -> rb.require(CgsAmmo.LEAD_BALLS.get()))
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe NAIL = this.create("nail", (b) -> {
        return b.require(TagKeys.IRON_NUGGET)
                .transitionTo(Items.IRON_NUGGET)
                .addOutput(new ItemStack(CgsAmmo.NAIL.get(), 6), 1.0F)
                .addStep(CuttingRecipe::new, (rb) -> rb)
                .addStep(PressingRecipe::new, (rb) -> rb)
                .loops(1);
    });

    BaseRecipeProvider.GeneratedRecipe STEEL_NAIL = this.create("steel_nail", (b) -> {
        return b.require(TagKeys.IRON_NUGGET)
                .transitionTo(CgsItems.STEEL_NUGGET.get())
                .addOutput(new ItemStack(CgsAmmo.STEEL_NAIL.get(), 6), 1.0F)
                .addStep(CuttingRecipe::new, (rb) -> rb)
                .addStep(PressingRecipe::new, (rb) -> rb)
                .loops(1);
    });


    private static @NotNull UnaryOperator<ProcessingRecipeBuilder<DeployerApplicationRecipe>> noConsumeTool(RegistryObject<Item> pressFormShotgun) {
        return (rb) -> rb.toolNotConsumed().require(pressFormShotgun.get());
    }
}
