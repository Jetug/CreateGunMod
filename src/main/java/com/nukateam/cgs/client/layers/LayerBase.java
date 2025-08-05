package com.nukateam.cgs.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.core.animatable.GeoAnimatable;
import mod.azure.azurelib.renderer.GeoRenderer;
import mod.azure.azurelib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import static com.nukateam.ntgl.client.util.ClientDebug.*;

public class LayerBase<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    public LayerBase(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    protected void renderLayer(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, MultiBufferSource bufferSource, float partialTick, int packedLight, ResourceLocation texture) {
        var renderTypeNew = RenderType.armorCutoutNoCull(texture);
        poseStack.pushPose();
        {
            poseStack.translate(-0.5, -0.5, -0.5);
            poseStack.translate(0, -16 / 100d / 16D, 0);
            poseStack.translate( X / 100d / 16d, Y / 100d / 16d, Z / 10d / 16d);

            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable,
                    renderTypeNew, bufferSource.getBuffer(renderTypeNew),
                    partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
        }
        poseStack.popPose();
    }
}
