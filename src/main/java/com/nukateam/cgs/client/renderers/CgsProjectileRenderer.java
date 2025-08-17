package com.nukateam.cgs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nukateam.cgs.client.model.ProjectileModel;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import mod.azure.azurelib.core.animatable.GeoAnimatable;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CgsProjectileRenderer<T extends ProjectileEntity & GeoAnimatable> extends GeoEntityRenderer<T> {
    public CgsProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ProjectileModel<>());
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (!entity.isVisible() || entity.tickCount <= 1)
            return;

        poseStack.pushPose();
        {
//            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        }
        poseStack.popPose();
    }
}
