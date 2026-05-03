package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.annotations.ItemModelGen;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.util.HashMap;

import static com.nukateam.cgs.common.datagen.DataGenConfig.dataGenClasses;
import static com.nukateam.cgs.common.datagen.DataGenUtils.handleFields;

@SuppressWarnings("unchecked")
public class CgsItemModelProvider extends ItemModelProvider {
    public CgsItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DataGenConfig.DATA_MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        for(var clazz : dataGenClasses){
            handleFields(clazz, ItemModelGen.class, this::handleDataGenField);
        }
    }

    private void handleDataGenField(Object obj, ItemModelGen annotation) {
        if (obj instanceof DeferredHolder<?, ?>) {
            switch (annotation.type()) {
                case ITEM -> genItems((DeferredHolder<Item, Item>) obj, annotation);
                case BLOCK -> blockModel((DeferredHolder<Block, Block>) obj);
            }
        } else if (obj instanceof HashMap<?,?>) {
            var storage = (HashMap<?, DeferredHolder<Item, Item>>) obj;
            for (var item : storage.values()){
                genItems(item, annotation);
            }
        }
    }

    private void genItems(DeferredHolder<Item, Item> item, ItemModelGen annotation) {
        var modelFile = getModelFile(annotation.parent().getPath());

        switch (annotation.parent()) {
            case SPAWN_EGG -> spawnEggModel(item, modelFile);
            default -> itemModel(item, modelFile, annotation);
        }
    }

    private ModelFile getModelFile(String path) {
        return getExistingFile(ResourceLocation.parse(path));
    }

//    private void spawnEggModel(DeferredHolder<Item, Item> egg) {
//        withExistingParent(egg.getId().getPath(), new ResourceLocation("item"));
//    }

    private void blockModel(DeferredHolder<Block, ? extends Block> block) {
        var path = block.getId().getPath();
        var loc = modLoc("block/" + block.getId().getPath());
        withExistingParent(path, loc);
    }

    private void blockModel(DeferredHolder<Block, ? extends Block> block, String suffix) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath() + "_" + suffix));
    }

    private void blockItemModel(DeferredHolder<?, ?> block, DeferredHolder<?, ?> textureBlock, ModelFile modelFile) {
        getBuilder(block.getId().getPath()).parent(modelFile).texture("layer0", "block/" + textureBlock.getId().getPath());
    }

    private ItemModelBuilder itemModel(DeferredHolder<?, ?> item, ModelFile modelFile, ItemModelGen dataGen) {
        var path = item.getId().getPath();

        var texture = "item/";

        if(!dataGen.path().isEmpty())
            texture += dataGen.path() + "/";
        if(dataGen.ownDir())
            texture += path.split("_")[0] + "/";

        return getBuilder(path).parent(modelFile).texture("layer0", texture + item.getId().getPath());
    }

    private ItemModelBuilder spawnEggModel(DeferredHolder<?, ?> item, ModelFile modelFile) {
        return getBuilder(item.getId().getPath()).parent(modelFile);
    }

    private void itemModelWithSuffix(DeferredHolder<?, ?> item, ModelFile modelFile, String suffix) {
        getBuilder(item.getId().getPath() + "_" + suffix).parent(modelFile).texture("layer0", "item/" + item.getId().getPath() + "_" + suffix);
    }

    private ModelFile.ExistingModelFile getModel(DeferredHolder<?, ?> item, String suffix) {
        return new ModelFile.ExistingModelFile(modLoc("item/" + item.getId().getPath() + "_" + suffix), existingFileHelper);
    }
}
