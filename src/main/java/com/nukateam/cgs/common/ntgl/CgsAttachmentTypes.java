package com.nukateam.cgs.common.ntgl;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.common.base.holders.AttachmentType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.nukateam.cgs.Gunsmithing.cgsResource;
import static com.nukateam.ntgl.common.base.holders.AttachmentType.registerType;

public class CgsAttachmentTypes {
    public static AttachmentType ENGINE = new AttachmentType(cgsResource("engine"));
    public static AttachmentType FRAME = new AttachmentType(cgsResource("frame"));
    public static AttachmentType CHAMBER = new AttachmentType(cgsResource("chamber"));

    static {
        registerType(ENGINE);
        registerType(FRAME);
        registerType(CHAMBER);
    }
}
