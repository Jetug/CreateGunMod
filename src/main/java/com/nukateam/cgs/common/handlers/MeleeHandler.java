package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.faundation.item.guns.HammerItem;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.cgs.common.utils.BreakHandler;
import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.holders.WeaponMode;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.common.event.MeleeAttackEvent;
import com.nukateam.ntgl.common.util.util.StackUtils;
import com.simibubi.create.content.kinetics.saw.TreeCutter;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MeleeHandler {
    public static BreakHandler HAMMER_HANDLER = new BreakHandler(
            MeleeHandler::isToolTierSufficient,
            MeleeHandler::isMineable,
            MeleeHandler::isCanDrop);

    public static BreakHandler AXE_HANDLER = new BreakHandler(
            MeleeHandler::isToolTierSufficient,
            MeleeHandler::isMineableAxe,
            MeleeHandler::isCanDropAxe);

    @SubscribeEvent
    public static void onMelee(MeleeAttackEvent.Pre event) {
        var data = event.getData();
        var stack = event.getData().weapon;
        var isSecondaryMode = data.weaponMode == WeaponMode.SECONDARY;
        var isHammer = stack != null && stack.getItem() == CgsWeapons.HAMMER.get();

        if(!event.isClient() && isSecondaryMode && isHammer && event.getEntity() instanceof ServerPlayer player){
            var entity = event.getEntity();

            if (!player.isCreative()) {
                WeaponStateHelper.consumeAmmo(data);
            }

            if(event.getTargets().isEmpty()) {
                hitBlock(player, data, entity);
            }
        }
    }

    private static void hitBlock(ServerPlayer player, WeaponData data, LivingEntity entity) {
        assert data.weapon != null;
        var head = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, data.weapon);

        if (head.getItem() instanceof HammerHeadItem headItem && HammerItem.isPowered(data)
                && !entity.hasEffect(MobEffects.DIG_SLOWDOWN)) {
            var reach = WeaponModifierHelper.getMeleeDistance(data);
            var isShotPowered = !WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, data.weapon).isEmpty();
            var level = player.level();


            var hitResult = getBlockHitResult(player, reach);
            if (hitResult.getType() != HitResult.Type.BLOCK) return;

            if (headItem.getHeadType() == HammerHeadItem.Type.HAMMER) {
                if (isShotPowered) {
                    breakBlocks3x3(player, headItem.getTier(), HAMMER_HANDLER, head, hitResult);
                }
                else {
                    breakBlockAt(player, headItem.getTier(), head, hitResult.getBlockPos(), HAMMER_HANDLER);
//
//                    breakBlocks2x1(player, headItem.getTier(), HAMMER_HANDLER, head, hitResult);
                }
            } else if (headItem.getHeadType() == HammerHeadItem.Type.AXE) {
                if (isShotPowered) {
                    var hitPos = hitResult.getBlockPos();

                    var planeDirs = getPlaneDirections(hitResult.getDirection());
                    var breakingPos = hitPos
                            .relative(planeDirs[0], -1)
                            .relative(planeDirs[1], 0);

                    TreeCutter.findTree(level, breakingPos, level.getBlockState(breakingPos))
                            .destroyBlocks(level, null, (pos, stack) -> {
//                                var distance = (float) Math.sqrt(pos.distSqr(breakingPos));
                                var dropPos = VecHelper.getCenterOf(pos);
                                var itemEntity = new ItemEntity(level, dropPos.x, dropPos.y, dropPos.z, stack);
//                                itemEntity.setDeltaMovement(Vec3.atLowerCornerOf(breakingPos.subtract(breakingPos))
//                                        .scale(distance / 20f));
                                level.addFreshEntity(itemEntity);
                            });

                } else {
                    breakBlockAt(player, headItem.getTier(), head, hitResult.getBlockPos(), AXE_HANDLER);
//
//                    breakBlocks2x1(player, headItem.getTier(), AXE_HANDLER, head, reach);
                }
            }
        }
    }

    private static void breakBlocks2x1(ServerPlayer player, Tier toolTier, BreakHandler handler, ItemStack stack, BlockHitResult hitResult) {
        var centerPos = hitResult.getBlockPos();
        var planeDirs = getPlaneDirections(hitResult.getDirection());

        var targetPos = centerPos
                .relative(planeDirs[0], -1)
                .relative(planeDirs[1], 0);

        breakBlockAt(player, toolTier, stack, centerPos, handler);
        breakBlockAt(player, toolTier, stack, targetPos, handler);
    }

    private static void breakBlocks3x3(ServerPlayer player, Tier toolTier, BreakHandler handler, ItemStack stack, BlockHitResult hitResult) {
        var centerPos = hitResult.getBlockPos();
        var planeDirs = getPlaneDirections(hitResult.getDirection());

        for (int u = -1; u <= 1; u++) {
            for (int v = -1; v <= 1; v++) {
                var targetPos = centerPos
                        .relative(planeDirs[0], u)
                        .relative(planeDirs[1], v);

                breakBlockAt(player, toolTier, stack, targetPos, handler);
            }
        }
    }

    private static @NotNull BlockHitResult getBlockHitResult(ServerPlayer player, float reach) {
        var start = player.getEyePosition(1.0F);
        var look = player.getViewVector(1.0F);
        var end = start.add(look.x * reach, look.y * reach, look.z * reach);

        return player.level().clip(
                new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)
        );
    }

    private static void breakBlockAt(ServerPlayer player, Tier toolTier, ItemStack stack,
                                     BlockPos targetPos, BreakHandler handler) {
        var blockState = player.level().getBlockState(targetPos);
        var canDistroy = blockState.getDestroySpeed(player.level(), targetPos) >= 0;
        var canGrief = Config.COMMON.gameplay.griefing.enableBlockRemovalOnExplosions.get();

        if(canGrief && canDistroy && handler.isToolTierSufficient(blockState, toolTier) && handler.isMineable(blockState)) {
            if(!player.isCreative()) {
                StackUtils.damageItem(stack, 1);
            }
            player.level().destroyBlock(targetPos, handler.isCanDrop(blockState), player);
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
//        return !blockState.is(BlockTags.MINEABLE_WITH_AXE) && !blockState.is(BlockTags.MINEABLE_WITH_HOE);
//        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)
//                || blockState.is(BlockTags.MINEABLE_WITH_SHOVEL);

        return true;
    }

    private static boolean isCanDrop(BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) || blockState.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    private static boolean isMineableAxe(BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_AXE) || blockState.is(BlockTags.MINEABLE_WITH_HOE);
//        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)
//                || blockState.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    private static boolean isCanDropAxe(BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_AXE) || blockState.is(BlockTags.MINEABLE_WITH_HOE);
    }

    private static Direction[] getPlaneDirections(Direction face) {
        return switch (face.getAxis()) {
            case Y -> new Direction[]{Direction.EAST, Direction.SOUTH};
            case Z -> new Direction[]{Direction.UP, Direction.EAST};
            case X -> new Direction[]{Direction.UP, Direction.SOUTH};
        };
    }
}
