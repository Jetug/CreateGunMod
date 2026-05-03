package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.datagen.DataGenConfig;
import com.nukateam.cgs.common.datagen.DataGenUtils;
import com.nukateam.cgs.common.datagen.annotations.BlockStateGen;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

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
        if (obj instanceof DeferredHolder<?, ?> registryObject && registryObject.get() instanceof Block) {
            blockWithItem((DeferredHolder<Block, Block>)registryObject);
        }
    }

    private void blockWithItem(DeferredHolder<Block, Block> blockDeferredHolder) {
        simpleBlockWithItem(blockDeferredHolder.get(), cubeAll(blockDeferredHolder.get()));
    }

    private void simpleBlock(DeferredHolder<Block, Block> blockDeferredHolder) {
        simpleBlock(blockDeferredHolder.get(), cubeAll(blockDeferredHolder.get()));
    }

    private void layeredBlockWithItem(DeferredHolder<Block, Block> blockDeferredHolder) {
        var block = blockDeferredHolder.get();
        var blockName = blockDeferredHolder.getId().getPath();

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
