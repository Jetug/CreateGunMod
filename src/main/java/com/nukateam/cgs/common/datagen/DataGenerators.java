package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.common.datagen.providers.*;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
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

        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new CgsMechanicalCraftingRecipeGen(packOutput));

        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, fileHelper));
        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagGenerator(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));
//        generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModPoiTypeTagsProvider(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

    }
}
