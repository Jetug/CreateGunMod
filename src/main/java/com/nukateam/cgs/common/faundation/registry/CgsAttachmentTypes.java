package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import net.minecraft.resources.ResourceLocation;

import static com.nukateam.ntgl.common.base.holders.AttachmentType.registerType;

public class CgsAttachmentTypes {
    public static AttachmentType ENGINE = new AttachmentType(new ResourceLocation(Gunsmithing.MOD_ID,"engine"));

    static {
        registerType(ENGINE);
    }
}
