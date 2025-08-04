package com.nukateam.cgs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicGunRenderer;
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
            poseStack.translate(1 / 16D, -3 / 16D, -3 / 16D);
        }
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void renderArms(PoseStack poseStack, GeoBone bone, int packedLight, int packedOverlay) {
        var hasExtendoGrip = currentEntity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();
        if (!hasExtendoGrip) {
            super.renderArms(poseStack, bone, packedLight, packedOverlay);
        }
    }
}
