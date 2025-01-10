package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, fileHelper));
    }
}
