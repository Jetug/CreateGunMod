package com.nukateam.cgs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.cgs.client.model.GatlingModel;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.event.InputEvents;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import com.nukateam.ntgl.common.util.data.Rgba;
import com.simibubi.create.AllItems;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BaseGunRenderer extends DynamicGunRenderer<GunAnimator> {
    public BaseGunRenderer() {
        super(new GeoGunModel());
    }

    public BaseGunRenderer(GeoModel model) {
        super(model);
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable RenderType renderType,
                       @Nullable VertexConsumer buffer, int packedLight) {

        poseStack.pushPose();
        var hasExtendoGrip = entity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();
        if(hasExtendoGrip && transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            poseStack.translate(InputEvents.X / 16D, InputEvents.Y / 16D, InputEvents.Z / 16D);
            poseStack.translate(1 / 16D, -3 / 16D, -3 / 16D);
        }
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void renderArms(PoseStack poseStack, GunAnimator animatable, GeoBone bone,
                              RenderType renderType, MultiBufferSource bufferSource, boolean isReRender,
                              float partialTick, int packedLight, int packedOverlay, Rgba rgba) {
        var hasExtendoGrip = currentEntity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();
        if (hasExtendoGrip) return;

        super.renderArms(poseStack, animatable, bone, renderType,
                bufferSource, isReRender, partialTick,
                packedLight, packedOverlay, rgba);
    }

    @Override
    protected void renderMuzzleFlash(PoseStack poseStack) {
        super.renderMuzzleFlash(poseStack);
    }
}
