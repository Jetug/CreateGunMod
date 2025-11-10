package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.common.datagen.providers.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DataGenConfig.DATA_MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var fileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new CgsItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeServer(), new CgsRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new CgsMechanicalCraftingRecipeGen(packOutput));
        generator.addProvider(event.includeServer(), new CgsSequencedAssemblyRecipeGen(packOutput));
        generator.addProvider(event.includeServer(), new CgsMixingRecipeGen(packOutput));

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
