package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import com.nukateam.cgs.common.utils.GunUtils;
//import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.common.registry.AmmoHolders;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.cgs.client.renderers.weapon.BaseWeaponRenderer;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CgsGunItem extends WeaponItem {
    private final Lazy<BaseWeaponRenderer> RENDERER = Lazy.of(() -> new BaseWeaponRenderer());

    public CgsGunItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DynamicGeoItemRenderer<WeaponAnimator> getRenderer() {
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
            var gunData = new WeaponData(gun, livingEntity);
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        var gunData = new WeaponData(stack, null);
        var fuelTypes = WeaponModifierHelper.getAllFuel(gunData);

        if(!fuelTypes.isEmpty()) {
            tooltip.add(Component.translatable("info.cgs.fuel").withStyle(ChatFormatting.GRAY));
        }
    }

    private static void onEngineTick(WeaponData data) {
        var fuel = WeaponModifierHelper.getAllFuel(data);

        if(!fuel.isEmpty() && isInHand(data)){
            if(fuel.contains(CgsAmmoHolders.BURNABLE)){
                FuelUtils.addFuel(data, CgsAmmoHolders.BURNABLE, -1);
            }
        }
    }

    private static void fillWater(WeaponData gunData) {
        var types = WeaponModifierHelper.getAllFuel(gunData);
        if(gunData.wielder.isInWater() && types.contains(CgsAmmoHolders.WATER)){
            var maxWater = WeaponModifierHelper.getMaxFuel(CgsAmmoHolders.WATER.getId(), gunData);
            FuelUtils.setFuel(gunData.weapon, CgsAmmoHolders.WATER, maxWater);
        }
    }

    private static boolean isInHand(WeaponData data) {
        var mainHand = data.wielder.getMainHandItem();
        var offHand = data.wielder.getOffhandItem();

        var oneHanded1 = WeaponModifierHelper.isOneHanded(new WeaponData(mainHand, data.wielder));
        var oneHanded2 = WeaponModifierHelper.isOneHanded(new WeaponData(offHand, data.wielder));

        return mainHand == data.weapon || (offHand == data.weapon && oneHanded1 && oneHanded2);
    }
}