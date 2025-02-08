package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.data.DataGen;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Gunsmithing.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        generate(ModGuns.class);
        generate(ModItems.class);
        generate(ModBlocks.class);
    }

    private void generate(Class<?> modItemsClass) {
        var fields = modItemsClass.getFields();

        try {
            for (var field : fields) {
                var obj = field.get(null);

                if (field.isAnnotationPresent(DataGen.class)) {
                    var annotation = field.getAnnotation(DataGen.class);

                    if (obj instanceof RegistryObject<?>) {
                        switch (annotation.type()) {
                            case ITEM -> genItems((RegistryObject<Item>) obj, annotation);
                            case BLOCK -> blockModel((RegistryObject<Block>) obj);
                        }
                    } else if (obj instanceof HashMap<?,?>) {
                        var storage = (HashMap<?, RegistryObject<Item>>) obj;
                        for (var item : storage.values()){
                            genItems(item, annotation);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Gunsmithing.LOGGER.error(e.getMessage(), e);
        }
    }

    private void genItems(RegistryObject<Item> item, DataGen annotation) {
        var modelFile = getModelFile(annotation.parent().getPath());

        switch (annotation.parent()) {
            case SPAWN_EGG -> spawnEggModel(item, modelFile);
            default -> itemModel(item, modelFile, annotation);
        }
    }

    private ModelFile getModelFile(String path) {
        return getExistingFile(new ResourceLocation(path));
    }

//    private void spawnEggModel(RegistryObject<Item> egg) {
//        withExistingParent(egg.getId().getPath(), new ResourceLocation("item"));
//    }

    private void blockModel(RegistryObject<? extends Block> block) {
        var path = block.getId().getPath();
        var loc = modLoc("block/" + block.getId().getPath());
        withExistingParent(path, loc);
    }

    private void blockModel(RegistryObject<? extends Block> block, String suffix) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath() + "_" + suffix));
    }

    private void blockItemModel(RegistryObject<?> block, RegistryObject<?> textureBlock, ModelFile modelFile) {
        getBuilder(block.getId().getPath()).parent(modelFile).texture("layer0", "block/" + textureBlock.getId().getPath());
    }

    private ItemModelBuilder itemModel(RegistryObject<?> item, ModelFile modelFile, DataGen dataGen) {
        var path = item.getId().getPath();

        var texture = "item/";

        if(!dataGen.path().isEmpty())
            texture += dataGen.path() + "/";
        if(dataGen.ownDir())
            texture += path.split("_")[0] + "/";

        return getBuilder(path).parent(modelFile).texture("layer0", texture + item.getId().getPath());
    }

    private ItemModelBuilder spawnEggModel(RegistryObject<?> item, ModelFile modelFile) {
        return getBuilder(item.getId().getPath()).parent(modelFile);
    }

    private void itemModelWithSuffix(RegistryObject<?> item, ModelFile modelFile, String suffix) {
        getBuilder(item.getId().getPath() + "_" + suffix).parent(modelFile).texture("layer0", "item/" + item.getId().getPath() + "_" + suffix);
    }

    private ModelFile.ExistingModelFile getModel(RegistryObject<?> item, String suffix) {
        return new ModelFile.ExistingModelFile(modLoc("item/" + item.getId().getPath() + "_" + suffix), existingFileHelper);
    }
}
