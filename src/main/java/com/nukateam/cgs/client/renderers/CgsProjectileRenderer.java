package com.nukateam.cgs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nukateam.cgs.client.model.ProjectileModel;
import com.nukateam.ntgl.client.util.ClientDebug;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import mod.azure.azurelib.core.animatable.GeoAnimatable;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CgsProjectileRenderer<T extends ProjectileEntity & GeoAnimatable> extends GeoEntityRenderer<T> {
    public static final int MAX_SIZE_TICK = 10;

    public CgsProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ProjectileModel<>());
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (!entity.isVisible() || entity.tickCount <= 1)
            return;

        poseStack.pushPose();
        {

            var scale = 1f;

            if(entity.tickCount < MAX_SIZE_TICK) {
                scale = (entity.tickCount + partialTick) / MAX_SIZE_TICK;
            }

            poseStack.scale(scale, scale, scale);

            poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        }
        poseStack.popPose();
    }
}
