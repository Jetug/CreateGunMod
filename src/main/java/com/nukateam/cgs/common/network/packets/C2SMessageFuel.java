package com.nukateam.cgs.common.network.packets;

import com.mrcrayfish.framework.api.network.MessageContext;
import com.mrcrayfish.framework.api.network.message.PlayMessage;
import com.nukateam.ntgl.common.base.utils.FuelUtils;
import com.nukateam.cgs.common.ntgl.CgsFuel;
import com.nukateam.ntgl.common.base.holders.FuelType;
import com.nukateam.ntgl.common.util.util.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;

import static com.nukateam.cgs.common.utils.GunUtils.fillFuel;

public class C2SMessageFuel extends PlayMessage<C2SMessageFuel> {
    public C2SMessageFuel() {}

    @Override
    public void encode(C2SMessageFuel message, FriendlyByteBuf buffer) {
//        buffer.writeBoolean(message.aiming);
    }

    @Override
    public C2SMessageFuel decode(FriendlyByteBuf buffer) {
        return new C2SMessageFuel();
    }

    @Override
    public void handle(C2SMessageFuel message, MessageContext supplier) {
        supplier.execute((() ->
        {
            var player = supplier.getPlayer();
            if (player != null && !player.isSpectator()) {
                var mainHandItem = player.getMainHandItem();
                var offhandItem = player.getOffhandItem();

                if (GunModifierHelper.isGun(mainHandItem)) {
                    fillFuel(new GunData(mainHandItem, player), offhandItem);
                }
                else if (GunModifierHelper.isGun(offhandItem)) {
                    fillFuel(new GunData(offhandItem, player), mainHandItem);
                }
            }
        }));
        supplier.setHandled(true);
    }

}
