package com.nukateam.cgs.common.datagen.providers.create;

import com.nukateam.cgs.Gunsmithing;
import com.simibubi.create.api.data.recipe.DeployingRecipeGen;
import net.minecraft.data.PackOutput;

public class CgsDeployingRecipeGen extends DeployingRecipeGen {
    public CgsDeployingRecipeGen(PackOutput output) {
        super(output, Gunsmithing.MOD_ID);
    }
}
