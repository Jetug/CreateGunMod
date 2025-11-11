package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.block.GuanoPileBlock;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.world.level.block.Block.popResource;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID)
public class GuanoAccumulationHandler {
    private static final Map<BlockPos, Long> batTrackingMap = new HashMap<>();
    private static final int ACCUMULATION_TIME = 30 * 20;
    private static final double ACCUMULATION_CHANCE = 0.1;

    @SubscribeEvent
    public static void onBatUpdate(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Bat bat)) return;
        if (bat.level().isClientSide) return;

        var level = bat.level();
        var batPos = bat.blockPosition();

        if (isValidForGuanoAccumulation(bat, level, batPos)) {
            var groundPos = findGroundBelowBat(level, batPos);

            if (groundPos != null) {
                batTrackingMap.merge(groundPos, 1L, Long::sum);
                var timeSpent = batTrackingMap.get(groundPos);

                if (timeSpent >= ACCUMULATION_TIME) {
                    tryAccumulateGuano(level, groundPos);
                    batTrackingMap.remove(groundPos);
                }
            }
        } else {
            batTrackingMap.entrySet().removeIf(entry ->
                    entry.getKey().closerToCenterThan(bat.position(), 2.0));
        }
    }

    private static boolean isValidForGuanoAccumulation(Bat bat, Level level, BlockPos pos) {
        return bat.isResting() &&
                level.getMaxLocalRawBrightness(pos) < 5 &&
                !level.isRainingAt(pos);
    }

    private static BlockPos findGroundBelowBat(Level level, BlockPos batPos) {
        for (int i = 1; i <= 5; i++) {
            BlockPos checkPos = batPos.below(i);
            BlockState state = level.getBlockState(checkPos);
            if (state.isSolidRender(level, checkPos) &&
                    Block.isFaceFull(state.getCollisionShape(level, checkPos), Direction.UP)) {
                return checkPos.above();
            }
        }
        return null;
    }

    private static void tryAccumulateGuano(Level level, BlockPos pos) {
        if (level.random.nextDouble() < ACCUMULATION_CHANCE) {
            var currentState = level.getBlockState(pos);

            if (currentState.isAir()) {
                level.setBlock(pos, CgsBlocks.GUANO_BLOCK.get().defaultBlockState(), 3);
            } else if (currentState.getBlock() instanceof GuanoPileBlock) {
                int layers = currentState.getValue(SnowLayerBlock.LAYERS);
                if (layers < 8) {
                    level.setBlock(pos, currentState.setValue(SnowLayerBlock.LAYERS, layers + 1), 3);
                }
                else {
                    popResource(level, pos, new ItemStack(CgsBlocks.GUANO_BLOCK.get()));
                }
            }
        }
    }
}