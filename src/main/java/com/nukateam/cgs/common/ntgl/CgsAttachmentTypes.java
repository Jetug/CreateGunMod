package com.nukateam.cgs.common.ntgl;

import com.nukateam.ntgl.common.data.holders.AttachmentType;

import static com.nukateam.cgs.Gunsmithing.cgsResource;

public class CgsAttachmentTypes {
    public static AttachmentType ENGINE = new AttachmentType(cgsResource("engine"));
    public static AttachmentType FRAME = new AttachmentType(cgsResource("frame"));
    public static AttachmentType CHAMBER = new AttachmentType(cgsResource("chamber"));

    static {
        AttachmentType.registerType(ENGINE);
        AttachmentType.registerType(FRAME);
        AttachmentType.registerType(CHAMBER);
    }
}
