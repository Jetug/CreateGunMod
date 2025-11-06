package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CgsPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public CgsPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
                                  @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, DataGenConfig.DATA_MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
//        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
//                .addOptional(new ResourceLocation(DataGenConfig.DATA_MOD_ID, "sound_poi"));
    }
}
