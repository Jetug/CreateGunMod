package com.nukateam.cgs.client.renderers.weapon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.util.ClientDebug;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.GeoBone;

public class FlintlockRenderer extends BaseWeaponRenderer {
    public FlintlockRenderer() {
        super();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, WeaponAnimator animatable, GeoBone bone, RenderType renderType,
                                  MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                                  float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        if (bone.getName().equals("scope") && hasRevolvingChambersEquiped()) {
            poseStack.translate(0, 0 , -3 / 16d);
//            poseStack.translate(0, 0 , -4 / 16d);
        }

        if (bone.getName().equals("magazine") && hasBlunderbussEquiped()) {
            var scale = 1.3f;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0, -2.2 / 16d, 0);
        }

        if (bone.getName().equals("melee2")) {
            var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, animatable.getStack());
            if(barrel.getItem() == CgsAttachments.FLINTLOCK_LONG_BARREL.get())
                bone.setHidden(true);
        }
        else bone.setHidden(false);

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    private boolean hasRevolvingChambersEquiped() {
        return WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE, gunStack).getItem() == CgsAttachments.REVOLVING_CHAMBERS.get();
    }

    private boolean hasBlunderbussEquiped() {
        var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, gunStack).getItem();
        return barrel == CgsAttachments.BLUNDERBUSS_BARREL.get() && barrel == CgsAttachments.LONG_BLUNDERBUSS_BARREL.get();
    }
}
