package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FluidContainerItem extends Item {
    private final Supplier<? extends Fluid> fluidSupplier;

    public FluidContainerItem(Supplier<? extends Fluid> fluid, Properties properties) {
        super(properties);
        this.fluidSupplier = fluid;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        if(this == CgsItems.LAVA_CONTAINER.get()){
            return 20000;
        }
        return -1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        var ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, level, stack, hitResult);
        if (ret != null) return ret;

        if (this.fluidSupplier.get() != Fluids.EMPTY) {
            var placementResult = tryPlaceFluid(player, level, hitResult, stack);
            if (placementResult.consumesAction()) {
                return InteractionResultHolder.sidedSuccess(handleContainerAfterUse(player, stack, false), level.isClientSide());
            }
        } else {
            var pickupResult = tryPickupFluid(level, hitResult);
            if (pickupResult.consumesAction()) {
                return InteractionResultHolder.sidedSuccess(handleContainerAfterUse(player, stack, true), level.isClientSide());
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    private InteractionResult tryPlaceFluid(Player player, Level level, BlockHitResult hitResult, ItemStack stack) {
        var fluid = fluidSupplier.get();
        if (fluid == Fluids.EMPTY) return InteractionResult.FAIL;

        var pos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var placePos = pos.relative(direction);

        if (player.mayUseItemAt(placePos, direction, player.getItemInHand(InteractionHand.MAIN_HAND))) {
            if (placeFluid(player, level, placePos, hitResult, fluid)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private boolean placeFluid(Player player, Level level, BlockPos pos, BlockHitResult hitResult, Fluid fluid) {
        if (!(fluid instanceof FlowingFluid)) return false;

        var state = level.getBlockState(pos);
        var canReplace = state.canBeReplaced(fluid);
        var isReplaceable = state.canBeReplaced();

        if (level.isEmptyBlock(pos) || canReplace || isReplaceable) {
            if (!level.isClientSide && isReplaceable && !state.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (level.setBlock(pos, fluid.defaultFluidState().createLegacyBlock(), 11) || state.getFluidState().isSource()) {
                return true;
            }
        }
        return false;
    }

    private InteractionResult tryPickupFluid(Level level, BlockHitResult hitResult) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var fluidState = state.getFluidState();

        if (fluidState.isSource() && fluidState.getType() != Fluids.EMPTY) {
            if (!level.isClientSide) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private ItemStack handleContainerAfterUse(Player player, ItemStack stack, boolean filled) {
        var newContainer = filled ?
                getFilledContainerForFluid(getTargetFluid(player.level(), player))
                : CgsItems.EMPTY_CONTAINER.get();

        if (newContainer == null) return stack;

        var newStack = new ItemStack(newContainer);

        if (stack.hasTag()) {
            newStack.setTag(stack.getTag().copy());
        }

        stack.shrink(1);

        if (stack.isEmpty()) {
            return newStack;
        } else {
            if (!player.getInventory().add(newStack)) {
                player.drop(newStack, false);
            }
            return stack;
        }
    }

    private Fluid getTargetFluid(Level level, Player player) {
        var hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        var pos = hitResult.getBlockPos();
        var fluidState = level.getFluidState(pos);
        return fluidState.getType();
    }

    private Item getFilledContainerForFluid(Fluid fluid) {
        for (var entry : CgsItems.CONTAINERS.entrySet()) {
            if (entry.getKey() == fluid) {
                return entry.getValue().get();
            }
        }
        return null;
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if (fluidSupplier.get() == Fluids.EMPTY) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(CgsItems.EMPTY_CONTAINER.get());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
    }
}