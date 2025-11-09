package com.nukateam.cgs.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.cgs.client.model.GatlingModel;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.ntgl.AttachmentMods;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.GeoBone;

public class FlintlockRenderer extends BaseGunRenderer{
    public FlintlockRenderer() {
        super();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, WeaponAnimator animatable, GeoBone bone, RenderType renderType,
                                  MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                                  float partialTick, int packedLight, int packedOverlay,
                                  float red, float green, float blue, float alpha) {

        if (bone.getName().equals("melee2")) {
            var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, animatable.getStack());
            if(barrel.getItem() == AttachmentItems.FLINTLOCK_LONG_BARREL.get())
                bone.setHidden(true);
        }
        else bone.setHidden(false);

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);
    }
}
