package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.common.datagen.providers.*;
import com.nukateam.cgs.common.datagen.providers.create.*;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DataGenConfig.DATA_MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var fileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new CgsItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeServer(), new CgsRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsMechanicalCraftingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsSequencedAssemblyRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsMixingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsPressingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsCrushingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsDeployingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsFillingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsEmptyingRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new CgsCuttingRecipeGen(packOutput, lookupProvider));

        generator.addProvider(event.includeServer(), CgsLootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new CgsBlockStateProvider(packOutput, fileHelper));
        CgsBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new CgsBlockTagGenerator(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new CgsItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));
//        generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
        generator.addProvider(event.includeServer(), new CgsPoiTypeTagsProvider(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new CgsWorldGenProvider(packOutput, lookupProvider));

    }
}
