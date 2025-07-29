package com.nukateam.cgs.common.faundation.item.attachments;

import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.holders.FuelType;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;

import java.util.Map;

public class SteamEngine extends AttachmentItem<GenericAttachment> {
    public static final int MAX_FUEL = 20000;
    public static final int MAX_WATER = 2000;

    public SteamEngine(AttachmentType type, GenericAttachment attachmentData, Properties properties) {
        super(type, attachmentData, properties);
    }

    public Map<FuelType, Integer> getMaxValues(){
        return Map.of(
                FuelType.BURNABLE, MAX_FUEL,
                FuelType.WATER, MAX_WATER);
    }
}
