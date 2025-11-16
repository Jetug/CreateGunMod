package com.nukateam.cgs.client.renderers.weapon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.model.gun.GeoWeaponModel;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.client.util.helpers.TransformUtils;
import com.simibubi.create.AllItems;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BaseWeaponRenderer extends DynamicWeaponRenderer<WeaponAnimator> {
    public BaseWeaponRenderer() {
        super(new GeoWeaponModel());
    }

    public BaseWeaponRenderer(GeoModel model) {
        super(model);
    }
    private ItemDisplayContext transformType;

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       @Nullable MultiBufferSource bufferSource, @Nullable RenderType renderType,
                       @Nullable VertexConsumer buffer, int packedLight) {
        this.transformType = transformType;
        poseStack.pushPose();
        var hasExtendoGrip = entity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();
        if(hasExtendoGrip && transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            poseStack.translate(1 / 16D, -3 / 16D, -3 / 16D);
        }
        super.render(entity, stack, transformType, poseStack, bufferSource, renderType, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, WeaponAnimator animatable, GeoBone bone, RenderType renderType,
                                  MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                                  float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {

        if(!TransformUtils.isFirstPerson(transformType)
                && (bone.getName().equals("muzzle_effect") || bone.getName().equals("muzzle_flash"))) {
            bone.setHidden(true);
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected void renderArms(PoseStack poseStack, GeoBone bone, int packedLight, int packedOverlay) {
        var hasExtendoGrip = currentEntity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP.get();
        if (!hasExtendoGrip) {
            super.renderArms(poseStack, bone, packedLight, packedOverlay);
        }
    }
}
