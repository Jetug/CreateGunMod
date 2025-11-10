package com.nukateam.cgs.common.faundation.block;

import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;

import javax.annotation.Nullable;

public class GuanoPileBlock extends SnowLayerBlock {
    public GuanoPileBlock(BlockBehaviour.Properties properties ) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return Block.isFaceFull(belowState.getCollisionShape(level, pos.below()), Direction.UP);
    }
    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        int layers = state.getValue(LAYERS);
        popResource(level, pos, new ItemStack(CgsItems.GUANO.get(), layers));
    }
}