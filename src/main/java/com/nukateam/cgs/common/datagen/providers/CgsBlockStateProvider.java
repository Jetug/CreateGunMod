package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.DataGenUtils;
import com.nukateam.cgs.common.datagen.annotations.BlockStateGen;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CgsBlockStateProvider extends BlockStateProvider {
    public CgsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DataGenConfig.DATA_MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(var clazz : DataGenConfig.dataGenClasses){
            DataGenUtils.handleFields(clazz, BlockStateGen.class, this::handleDataGenField);
        }

        layeredBlockWithItem(CgsBlocks.GUANO_BLOCK);
    }

    private void handleDataGenField(Object obj, BlockStateGen annotation) {
        if (obj instanceof RegistryObject<?> registryObject && registryObject.get() instanceof Block) {
            blockWithItem((RegistryObject<Block>)registryObject);
        }
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }


    private void simpleBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void layeredBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        var block = blockRegistryObject.get();
        var blockName = blockRegistryObject.getId().getPath();

        var layerModels = new ModelFile[8];
        for (int i = 0; i < 7; i++) {
            int layers = i + 1;
            var heightName = String.valueOf(layers * 2);
            layerModels[i] = models().withExistingParent(blockName + "_height" + heightName, "block/snow_height" + heightName)
                    .texture("texture", blockTexture(block))
                    .texture("particle", blockTexture(block));
        }
        layerModels[7] = models().cubeAll(blockName, blockTexture(block));

        getVariantBuilder(block).forAllStates(state -> {
            int layers = state.getValue(BlockStateProperties.LAYERS);
            ModelFile model = layerModels[layers - 1]; // layers от 1 до 8
            return ConfiguredModel.builder().modelFile(model).build();
        });

//        simpleBlockItem(block, layerModels[7]);
    }

    private boolean hasLayersProperty(Block block) {
        return block.getStateDefinition().getProperties().stream()
                .anyMatch(property -> property == BlockStateProperties.LAYERS);
    }
}
