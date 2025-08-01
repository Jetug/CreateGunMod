package com.nukateam.cgs.common.handlers;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModWeapons;
import com.nukateam.ntgl.common.event.GunFireEvent;
import com.nukateam.ntgl.common.event.MeleeAttackEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gunsmithing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MeleeHandler {
    @SubscribeEvent
    public static void onMelee(MeleeAttackEvent.Pre event) {
        if(event.getStack().getItem() == ModWeapons.HAMMER.get()
                && event.getTargets().isEmpty()
                && event.getEntity() instanceof ServerPlayer player){
            breakBlocks3x3(player);
        }
    }

    private static void breakBlocks3x3(ServerPlayer player) {
        double reach = 5.0;
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = start.add(look.x * reach, look.y * reach, look.z * reach);

        BlockHitResult hitResult = player.level().clip(
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

                player.gameMode.destroyBlock(targetPos);
            }
        }
    }

    private static Direction[] getPlaneDirections(Direction face) {
        return switch (face.getAxis()) {
            case Y -> new Direction[]{Direction.EAST, Direction.SOUTH};
            case Z -> new Direction[]{Direction.UP, Direction.EAST};
            case X -> new Direction[]{Direction.UP, Direction.SOUTH};
        };
    }
}
