package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.utils.GunUtils;
import com.nukateam.ntgl.common.data.holders.AmmoHolders;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.renderers.BaseGunRenderer;
import com.nukateam.ntgl.common.data.holders.AmmoHolders;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.interfaces.IGunModifier;
import com.nukateam.ntgl.common.data.GunData;
import com.nukateam.ntgl.common.util.util.GunModifierHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CgsGunItem extends WeaponItem {
    private final Lazy<BaseGunRenderer> RENDERER = Lazy.of(() -> new BaseGunRenderer());

    public CgsGunItem(Properties properties, IGunModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public DynamicGeoItemRenderer getRenderer() {
        return RENDERER.get();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack gun, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(gun, level, entity, pSlotId, pIsSelected);

        if (entity instanceof LivingEntity livingEntity) {
            var gunData = new GunData(gun, livingEntity);
            onEngineTick(gunData);
            fillWater(gunData);
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack gun, ItemStack fuel,
                                            Slot slot, ClickAction action,
                                            Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY) {
            GunUtils.playAttachSound(player, 0.5f);
            return GunUtils.fillFuel(gun, player, fuel);
        }
        else return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        var gunData = new GunData(stack, null);
        var fuelTypes = GunModifierHelper.getFuelTypes(gunData);

        if(!fuelTypes.isEmpty()) {
            tooltip.add(Component.translatable("info.cgs.fuel").withStyle(ChatFormatting.GRAY));
        }
    }

    private static void onEngineTick(GunData data) {
        var fuel = GunModifierHelper.getFuelTypes(data);

        if(!fuel.isEmpty() && isInHand(data)){
            if(fuel.contains(AmmoHolders.BURNABLE)){
                FuelUtils.addFuel(data, AmmoHolders.BURNABLE, -1);
            }
        }
    }

    private static void fillWater(GunData gunData) {
        var types = GunModifierHelper.getFuelTypes(gunData);

        if(gunData.shooter.isInWater() && types.contains(AmmoHolders.WATER)){
            var maxWater = GunModifierHelper.getMaxFuel(gunData, AmmoHolders.WATER);
            FuelUtils.setFuel(gunData.gun, AmmoHolders.WATER, maxWater);
        }
    }

    private static boolean isInHand(GunData data) {
        var mainHand = data.shooter.getMainHandItem();
        var offHand = data.shooter.getOffhandItem();

        var oneHanded1 = GunModifierHelper.isOneHanded(new GunData(mainHand, data.shooter));
        var oneHanded2 = GunModifierHelper.isOneHanded(new GunData(offHand, data.shooter));

        return mainHand == data.gun || (offHand == data.gun && oneHanded1 && oneHanded2);
    }
}