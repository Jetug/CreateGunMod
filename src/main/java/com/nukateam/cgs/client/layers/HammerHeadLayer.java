package com.nukateam.cgs.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.GunAnimator;
import com.nukateam.ntgl.common.data.config.gun.Gun;
import com.nukateam.ntgl.common.util.util.GunStateHelper;
import com.nukateam.ntgl.common.util.util.ResourceUtils;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;

public class HammerHeadLayer<T extends GunAnimator> extends LayerBase<T> {
    public static final String PATH = "textures/guns/hammer/";
    public static HashMap<ResourceLocation, Boolean> textures = new HashMap<>();
    public HammerHeadLayer(GeoRenderer<T> entityRenderer) {
        super(entityRenderer);
    }

    protected boolean resourceExists(ResourceLocation location){
        if (!textures.containsKey(location))
            textures.put(location, ResourceUtils.resourceExists(location));

        return textures.get(location);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {
        var texture = getHeadTexture(animatable);
        if(texture != null) {
            renderLayer(poseStack, animatable, bakedModel, bufferSource, partialTick, packedLight, texture);
        }
    }

    @Nullable
    private ResourceLocation getHeadTexture(T animatable) {
        var attachment = GunStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD, animatable.getStack());

        if(!attachment.isEmpty()){
            var head = (HammerHeadItem)attachment.getItem();
            var name = "hammer_" + head.getTier().toString().toLowerCase(Locale.ROOT) + ".png";
            return new ResourceLocation(Gunsmithing.MOD_ID, PATH + name);
        }
        return null;
    }
}