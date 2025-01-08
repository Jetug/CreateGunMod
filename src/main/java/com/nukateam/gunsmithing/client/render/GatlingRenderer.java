package com.nukateam.gunsmithing.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.gunsmithing.client.GatlingAnimator;
import com.nukateam.gunsmithing.client.model.GatlingModel;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.client.model.gun.GeoGunModel;
import com.nukateam.ntgl.client.render.renderers.gun.DynamicGunRenderer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.core.object.DataTicket;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GatlingRenderer extends DynamicGunRenderer<GatlingAnimator> {
    public static final DataTicket<Float> ROTATION = new DataTicket<>("rot", Float.class);

    public GatlingRenderer() {
        super(new GatlingModel());
    }

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable RenderType renderType,
                       @Nullable VertexConsumer buffer, int packedLight) {

        poseStack.pushPose();
        poseStack.translate(0, /*InputEvents.Y / 16D*/ - 6 / 16D,0);
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }

//    @Override
//    public void actuallyRender(PoseStack poseStack, GatlingAnimator animatable, BakedGeoModel model,
//                               RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
//                               boolean isReRender, float partialTick, int packedLight, int packedOverlay,
//                               float red, float green, float blue, float alpha) {
//
//        if (!isReRender) {
//            var animationState = new AnimationState<>(animatable, 0, 0, partialTick, false);
//            var instanceId = getInstanceId(animatable);
//            float rot = Mth.lerp(partialTick, animatable.prog0, animatable.prog);
//
//            animationState.setData(DataTickets.TICK, animatable.getTick(animatable));
//            animationState.setData(ROTATION, rot);
//
//            this.model.addAdditionalStateData(animatable, instanceId, animationState::setData);
////            this.model.handleAnimations(animatable, instanceId, animationState);
//        }
//
//        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender,
//                partialTick, packedLight, packedOverlay, red, green, blue, alpha);
//    }
}
