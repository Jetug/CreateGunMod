package com.nukateam.cgs.common.faundation.item.attachments;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.holders.FuelType;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HammerHeadItem extends AttachmentItem<GenericAttachment> {
    private final Tier tier;
    private final Type type;

    public HammerHeadItem(Tier tier, Type type, GenericAttachment attachmentData, Properties properties) {
        super(CgsAttachmentTypes.HEAD, attachmentData, properties);
        this.tier = tier;
        this.type = type;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair)  {
        return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public Tier getTier() {
        return tier;
    }

    public Type getHeadType() {
        return type;
    }

    public enum Type {
        HAMMER,
        AXE
    }
}
