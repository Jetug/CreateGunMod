package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.faundation.registry.items.ModWeapons;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.event.MeleeAttackEvent;
import com.nukateam.ntgl.common.util.util.StackUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.common.ForgeHooks.isCorrectToolForDrops;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MeleeHandler {
    @SubscribeEvent
    public static void onMelee(MeleeAttackEvent.Pre event) {
        if(event.getStack().getItem() == ModWeapons.HAMMER.get()
                && event.getTargets().isEmpty()
                && event.getEntity() instanceof ServerPlayer player){

            var head = Gun.getAttachmentItem(CgsAttachmentTypes.HEAD, event.getStack());

            if(head.getItem() instanceof HammerHeadItem headItem && headItem.getHeadType() == HammerHeadItem.Type.HAMMER){
                breakBlocks3x3(player, headItem.getTier(), head);
            }
        }
    }

    private static void breakBlocks3x3(ServerPlayer player, Tier toolTier, ItemStack stack) {
        var reach = 5.0;
        var start = player.getEyePosition(1.0F);
        var look = player.getViewVector(1.0F);
        var end = start.add(look.x * reach, look.y * reach, look.z * reach);

        var hitResult = player.level().clip(
                new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)
        );

        if (hitResult.getType() != HitResult.Type.BLOCK) return;

        Direction face = hitResult.getDirection();
        BlockPos centerPos = hitResult.getBlockPos();

        Direction[] planeDirs = getPlaneDirections(face);

        for (int u = -1; u <= 1; u++) {
            for (int v = -1; v <= 1; v++) {
                BlockPos targetPos = centerPos
                        .relative(planeDirs[0], u)
                        .relative(planeDirs[1], v);

                var blockState = player.level().getBlockState(targetPos);
                if(isToolTierSufficient(blockState, toolTier) && isMineable(blockState)) {
                    if(!player.isCreative())
                        StackUtils.damageItem(stack, 1);
                    player.level().destroyBlock(targetPos, true);
                }
            }
        }
    }

    public static boolean isToolTierSufficient(BlockState blockState, Tier toolTier) {
        if (blockState.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return toolTier.getLevel() >= Tiers.DIAMOND.getLevel();
        } else if (blockState.is(BlockTags.NEEDS_IRON_TOOL)) {
            return toolTier.getLevel() >= Tiers.IRON.getLevel();
        } else if (blockState.is(BlockTags.NEEDS_STONE_TOOL)) {
            return toolTier.getLevel() >= Tiers.STONE.getLevel();
        }

        return true;
    }

    private static boolean isMineable(BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    private static Direction[] getPlaneDirections(Direction face) {
        return switch (face.getAxis()) {
            case Y -> new Direction[]{Direction.EAST, Direction.SOUTH};
            case Z -> new Direction[]{Direction.UP, Direction.EAST};
            case X -> new Direction[]{Direction.UP, Direction.SOUTH};
        };
    }
}
