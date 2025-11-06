package com.nukateam.cgs.client.model;

import com.nukateam.ntgl.client.model.IGlowingModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

public class ProjectileModel<T extends Entity & GeoAnimatable> extends GeoModel<T> implements IGlowingModel<T> {
    @Override
    public ResourceLocation getModelResource(T animator) {
        return getResource(animator, "geo/projectile/", ".geo.json");
    }

    @Override
    public ResourceLocation getAnimationResource(T animator) {
        return getResource(animator, "animations/projectile/", ".animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animator) {
        var name = getName(animator);
        return getResource(animator, "textures/projectile/" + name + "/", ".png");
    }

    @Override
    public ResourceLocation getGlowingTextureResource(T animator) {
        var name = getName(animator);
        return getResource(animator, "textures/projectile/" + name + "/", "_glowmask.png");
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    public ResourceLocation getResource(T animator, String path, String extension) {
        var id = ForgeRegistries.ENTITY_TYPES.getKey(animator.getType());
        var modId = id.getNamespace();
        var name = id.getPath();
        return ResourceLocation.tryBuild(modId, path + name + extension);
    }

    private String getName(T animator) {
        return ForgeRegistries.ENTITY_TYPES.getKey(animator.getType()).getPath();
    }
}
