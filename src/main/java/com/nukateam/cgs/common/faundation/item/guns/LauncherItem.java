package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;

public class LauncherItem extends CgsGunItem {
    public LauncherItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return getClientDescriptionId(stack);
        }
        return super.getDescriptionId(stack);
    }

    @OnlyIn(Dist.CLIENT)
    private String getClientDescriptionId(ItemStack stack) {
        var magazineAttachment = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE,
                new WeaponData(stack, Minecraft.getInstance().player)).getItem();

        if(magazineAttachment == CgsAttachments.BALLISTAZOOKA.get()){
            return "item.cgs.ballistazooka";
        }

        return super.getDescriptionId(stack);
    }
}