package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.datagen.ModWorldGenProvider;
import com.nukateam.cgs.common.faundation.registry.ModItemModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var fileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, fileHelper));

//        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
//        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

//        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, fileHelper));
//        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, fileHelper));

//        ModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
//                new ModBlockTagGenerator(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));
//        generator.addProvider(event.includeServer(), new ModGlobalLootModifiersProvider(packOutput));
//        generator.addProvider(event.includeServer(), new ModPoiTypeTagsProvider(packOutput, lookupProvider, fileHelper));

        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
    }
}


